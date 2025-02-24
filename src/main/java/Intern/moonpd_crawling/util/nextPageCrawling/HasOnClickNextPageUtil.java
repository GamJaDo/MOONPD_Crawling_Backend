package Intern.moonpd_crawling.util.nextPageCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickNextPageUtil {

    public void goToNextPageByOnclick(WebDriver webDriver, String onClickNextPageScript) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(onClickNextPageScript);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute onclick script: " + onClickNextPageScript,
                e);
        }
    }
}
