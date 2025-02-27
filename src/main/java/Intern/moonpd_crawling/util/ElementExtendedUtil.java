package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.selector.extended.ExtendedLstSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedPdfSelectorType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedLstTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementExtendedUtil {

    public List<WebElement> getExtendedLstElements(String extendedLstIdentifier,
        ExtendedLstTagType extendedLstTagType, ExtendedLstSelectorType extendedLstSelectorType,
        List<WebElement> lstElements) {

        List<WebElement> filteredElements = new ArrayList<>();

        String xpath = "ancestor::*[local-name()='" + extendedLstTagType + "'";
        if (extendedLstIdentifier != null && !extendedLstIdentifier.isEmpty()) {
            if (extendedLstSelectorType.equals(ExtendedLstSelectorType.CLASS)) {
                xpath += " and contains(@class, '" + extendedLstIdentifier + "')";
            } else if (extendedLstSelectorType.equals(ExtendedLstSelectorType.STYLE)) {
                xpath += " and contains(@style, '" + extendedLstIdentifier + "')";
            } else if (extendedLstSelectorType.equals(ExtendedLstSelectorType.ID)) {
                xpath += " and contains(@id, '" + extendedLstIdentifier + "')";
            } else {
                throw new WebDriverException("Unsupported extendedSelectorType type");
            }
        }
        xpath += "]";

        for (WebElement lstElement : lstElements) {
            if (!lstElement.findElements(By.xpath(xpath)).isEmpty()) {
                filteredElements.add(lstElement);
            }
        }

        return filteredElements;
    }

    public List<WebElement> getExtendedPdfElements(String extendedPdfIdentifier,
        ExtendedPdfTagType extendedPdfTagType, ExtendedPdfSelectorType extendedPdfSelectorType,
        List<WebElement> pdfElements) {

        List<WebElement> filteredElements = new ArrayList<>();

        String xpath = "ancestor::*[local-name()='" + extendedPdfTagType + "'";
        if (extendedPdfIdentifier != null && !extendedPdfIdentifier.isEmpty()) {
            if (extendedPdfSelectorType.equals(ExtendedPdfSelectorType.CLASS)) {
                xpath += " and contains(@class, '" + extendedPdfIdentifier + "')";
            } else if (extendedPdfSelectorType.equals(ExtendedPdfSelectorType.STYLE)) {
                xpath += " and contains(@style, '" + extendedPdfIdentifier + "')";
            } else if (extendedPdfSelectorType.equals(ExtendedPdfSelectorType.ID)) {
                xpath += " and contains(@id, '" + extendedPdfIdentifier + "')";
            } else {
                throw new WebDriverException("Unsupported extendedSelectorType type");
            }
        }
        xpath += "]";

        for (WebElement pdfElement : pdfElements) {
            if (!pdfElement.findElements(By.xpath(xpath)).isEmpty()) {
                filteredElements.add(pdfElement);
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
