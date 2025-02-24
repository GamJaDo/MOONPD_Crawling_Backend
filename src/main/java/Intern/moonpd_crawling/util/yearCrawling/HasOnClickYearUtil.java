package Intern.moonpd_crawling.util.yearCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickYearUtil {

    public void goToYearByOnclick(WebDriver webDriver, String onClickYearScript) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(onClickYearScript);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute onclick script: " + onClickYearScript, e);
        }
    }
}
