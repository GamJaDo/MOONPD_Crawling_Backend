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

    public void goToNextPageByElement(WebDriver webDriver, String nextIdentifier,
        int currentPage) {

        WebElement nextElements = null;
        try {
            nextElements = webDriver.findElement(By.cssSelector("." + nextIdentifier));
        } catch(NoSuchElementException e) {
            nextElements = webDriver.findElement(By.id(nextIdentifier));
        }
        List<WebElement> allLinks = nextElements.findElements(By.cssSelector("a"));

        for (WebElement link : allLinks) {
            String href = link.getAttribute("href");

            if (href != null) {
                try {
                    String updatedHref = updatePageInString(href, currentPage);

                    ((JavascriptExecutor) webDriver).executeScript(
                        "window.location.href='" + updatedHref + "'");
                    return;
                } catch (NotFoundException e) {
                    System.out.println("Skipping href: " + href + " - " + e.getMessage());
                }
            }
        }
    }

    private String updatePageInString(String value, int currentPage) {
        int numberIndexStart = -1;
        int numberIndexEnd = -1;

        for (int i = value.length() - 1; i >= 0; i--) {
            if (Character.isDigit(value.charAt(i))) {
                if (numberIndexEnd == -1) {
                    numberIndexEnd = i;
                }
                numberIndexStart = i;
            } else if (numberIndexEnd != -1) {
                break;
            }
        }

        if (numberIndexStart == -1 || numberIndexEnd == -1) {
            throw new NotFoundException("No number found in the nextIdentifier string: " + value);
        }

        String prefix = value.substring(0, numberIndexStart);
        String suffix = value.substring(numberIndexEnd + 1);

        return prefix + currentPage + suffix;
    }
}
