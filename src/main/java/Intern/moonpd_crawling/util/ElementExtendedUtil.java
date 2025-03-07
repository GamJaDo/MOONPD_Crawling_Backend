package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementExtendedUtil {

    public List<WebElement> getExtendedElements(String extendedIdentifier, TagType extendedTagType,
        SelectorType extendedSelectorType, List<WebElement> elements) {

        List<WebElement> filteredElements = new ArrayList<>();

        String xpath = "ancestor::*[local-name()='" + extendedTagType + "'";
        if (extendedIdentifier != null && !extendedIdentifier.isEmpty()) {
            if (extendedSelectorType.equals(SelectorType.CLASS)) {
                xpath += " and contains(@class, '" + extendedIdentifier + "')";
            } else if (extendedSelectorType.equals(SelectorType.STYLE)) {
                xpath += " and contains(@style, '" + extendedIdentifier + "')";
            } else if (extendedSelectorType.equals(SelectorType.ID)) {
                xpath += " and contains(@id, '" + extendedIdentifier + "')";
            } else {
                throw new WebDriverException("Unsupported extendedSelectorType type");
            }
        }
        xpath += "]";

        for (WebElement element : elements) {
            if (!element.findElements(By.xpath(xpath)).isEmpty()) {
                filteredElements.add(element);
            }
        }

        return filteredElements;
    }

    public List<WebElement> getDescendantPdfTextElements(List<WebElement> elements) {
        List<WebElement> filteredElements = new ArrayList<>();

        for (WebElement element : elements) {
            if (element.getAttribute("innerHTML").contains("pdf")){
                filteredElements.add(element);
            }
        }

         return filteredElements;
    }
}
