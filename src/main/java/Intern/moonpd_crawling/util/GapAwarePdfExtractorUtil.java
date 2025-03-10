package Intern.moonpd_crawling.util;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class GapAwarePdfExtractorUtil {

    public List<WebElement> getPdfElementsFromBlogWrap(WebDriver webDriver, String extendedCssSelector,
        String parentCssSelector, String cssSelector) {

        List<WebElement> results = new ArrayList<>();
        WebElement extendedElement = webDriver.findElement(By.cssSelector(extendedCssSelector));
        List<WebElement> parentElements = extendedElement.findElements(By.cssSelector(parentCssSelector));

        for (WebElement parent : parentElements) {
            List<WebElement> pdfList = parent.findElements(By.cssSelector(cssSelector));
            if (pdfList.size() > 0) {
                results.add(pdfList.get(0));
            } else {
                results.add(null);
            }
        }
/*
        System.out.println("###############");
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) != null) {
                System.out.println("results[" + i + "]: " + results.get(i).getText());
            } else {
                System.out.println("results[" + i + "]: null");
            }
        }
        System.out.println("###############");
*/
        return results;
    }
}
