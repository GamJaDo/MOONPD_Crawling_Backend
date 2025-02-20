package Intern.moonpd_crawling.util.yearCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickYearUtil {

    public void goToYearByElement(WebDriver webDriver, String yearLink) {

        try {
            ((JavascriptExecutor) webDriver).executeScript(
                "window.location.href='" + yearLink + "'");
        } catch (NotFoundException e) {
            System.out.println("Skipping href: " + yearLink + " - " + e.getMessage());
        }
    }
}