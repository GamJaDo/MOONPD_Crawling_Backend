package Intern.moonpd_crawling.util.pdfCrawling;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v131.network.Network;
import org.openqa.selenium.devtools.v131.network.model.Response;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class ImgTagPdfUtil {

    public String getPdfLink(WebDriver driver, List<WebElement> pdfElements, int index) {
        // ChromeDriver에서만 동작하도록 확인
        if (!(driver instanceof ChromeDriver)) {
            throw new WebDriverException("This method works only with ChromeDriver.");
        }
        ChromeDriver chromeDriver = (ChromeDriver) driver;
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();

        // 네트워크 이벤트 수집 활성화
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // PDF 응답 URL을 저장할 AtomicReference 준비
        AtomicReference<String> pdfUrlRef = new AtomicReference<>();

        // 네트워크 응답 이벤트 리스너 등록
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            // MIME type에 "pdf"가 포함되어 있으면 PDF 다운로드 요청으로 판단
            if (response.getMimeType() != null && response.getMimeType().toLowerCase().contains("pdf")) {
                // 한 번만 저장 (이미 값이 설정되어 있으면 덮어쓰지 않음)
                pdfUrlRef.compareAndSet(null, response.getUrl());
            }
        });

        // 대상 요소 클릭 (PDF 다운로드 요청이 발생할 것으로 예상)
        pdfElements.get(index).click();

        // PDF 응답 URL이 캡처될 때까지 최대 10초 대기
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> pdfUrlRef.get() != null);
        } catch (Exception e) {
            // 네트워크 캡처 실패 시 네트워크 모니터링 종료 후 예외 발생
            devTools.send(Network.disable());
            devTools.disconnectSession();
            throw new WebDriverException("Timeout waiting for PDF network response", e);
        }

        String pdfUrl = pdfUrlRef.get();

        // 네트워크 모니터링 종료 및 DevTools 세션 해제
        devTools.send(Network.disable());
        devTools.disconnectSession();

        // (필요 시) 원래 페이지로 복귀
        driver.navigate().back();

        return pdfUrl;
    }
}
