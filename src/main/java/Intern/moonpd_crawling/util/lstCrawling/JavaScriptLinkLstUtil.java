package Intern.moonpd_crawling.util.lstCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptLinkLstUtil {

    public void goToJavaScriptLink(WebDriver webDriver, String yearLink) {

        try {
            ((JavascriptExecutor) webDriver).executeScript(
                "window.location.href='" + yearLink + "'");
        } catch (NotFoundException e) {
            System.out.println("Skipping href: " + yearLink + " - " + e.getMessage());
        }
    }
}
