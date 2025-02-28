package Intern.moonpd_crawling.util.nextPageCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HrefLinkNextPageUtil {

    public void goToNextPageByHref(WebDriver webDriver, String nextPageLink) {

        try {
            ((JavascriptExecutor) webDriver).executeScript(
                "window.location.href='" + nextPageLink + "'");
        } catch (NotFoundException e) {
            System.out.println("Skipping href: " + nextPageLink + " - " + e.getMessage());
        }
    }
}
