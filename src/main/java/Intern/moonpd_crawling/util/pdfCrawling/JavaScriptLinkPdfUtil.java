package Intern.moonpd_crawling.util.pdfCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptLinkPdfUtil {

    public String getPdfLinkByJavaScriptLink(WebDriver webDriver, String javaScriptLink) {

        String script = javaScriptLink.substring("javascript:".length());
        ((JavascriptExecutor) webDriver).executeScript(script);

        String pdfLink = webDriver.getCurrentUrl();
        webDriver.navigate().back();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return pdfLink;
    }
}
