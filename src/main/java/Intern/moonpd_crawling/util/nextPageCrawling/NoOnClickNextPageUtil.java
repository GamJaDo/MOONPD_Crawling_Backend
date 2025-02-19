package Intern.moonpd_crawling.util.nextPageCrawling;

import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickNextPageUtil {

    public void goToNextPageByElement(WebDriver webDriver, String nextPageLink) {

        try {
            ((JavascriptExecutor) webDriver).executeScript(
                "window.location.href='" + nextPageLink + "'");
        } catch (NotFoundException e) {
            System.out.println("Skipping href: " + nextPageLink + " - " + e.getMessage());
        }
    }
}
