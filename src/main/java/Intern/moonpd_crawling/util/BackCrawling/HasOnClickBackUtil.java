package Intern.moonpd_crawling.util.BackCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickBackUtil {

    public void goToBackByOnclick(WebDriver webDriver, String onClickBackScript) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(onClickBackScript);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute onclick script: " + onClickBackScript,
                e);
        }
    }
}
