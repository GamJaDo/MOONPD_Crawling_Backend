package Intern.moonpd_crawling.util.yearCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickYearUtil {

    public void goToYearByElement(WebDriver webDriver, WebElement yearLink) {

        String updatedHref = yearLink.getAttribute("href");

        try {
            ((JavascriptExecutor) webDriver).executeScript(
                "window.location.href='" + updatedHref + "'");
        } catch (NotFoundException e) {
            System.out.println("Skipping href: " + updatedHref + " - " + e.getMessage());
        }
    }
}