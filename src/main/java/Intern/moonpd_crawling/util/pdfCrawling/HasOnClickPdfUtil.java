package Intern.moonpd_crawling.util.pdfCrawling;

import Intern.moonpd_crawling.exception.WebDriverException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickPdfUtil {

    public String getPdfLinkByOnClick(WebDriver webDriver, String onClickPdfScript){

        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(onClickPdfScript);
        } catch (Exception e) {
            throw new WebDriverException("Failed to execute onclick script: " + onClickPdfScript);
        }

        return "temp";
    }
}
