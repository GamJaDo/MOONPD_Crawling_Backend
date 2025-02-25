package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.tag.parent.ParentExtendedLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementExtendedUtil {

    public List<WebElement> getExtendedLstElements(String parentExtendedLstIdentifier,
        ParentExtendedLstTagType parentExtendedLstTagType, List<WebElement> lstElements) {

        List<WebElement> filteredElements = new ArrayList<>();

        String xpath = "ancestor::*[local-name()='" + parentExtendedLstTagType + "'";
        if (parentExtendedLstIdentifier != null && !parentExtendedLstIdentifier.isEmpty()) {
            xpath += " and contains(@class, '" + parentExtendedLstIdentifier + "')";
        }
        xpath += "]";

        for (WebElement lstElement : lstElements) {
            if (!lstElement.findElements(By.xpath(xpath)).isEmpty()) {
                filteredElements.add(lstElement);
            }
        }

        return filteredElements;
    }

    public List<WebElement> getExtendedPdfElements(String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, List<WebElement> pdfElements) {

        List<WebElement> filteredElements = new ArrayList<>();

        String xpath = "ancestor::*[local-name()='" + parentExtendedPdfTagType + "'";
        if (parentExtendedPdfIdentifier != null && !parentExtendedPdfIdentifier.isEmpty()) {
            xpath += " and contains(@class, '" + parentExtendedPdfIdentifier + "')";
        }
        xpath += "]";

        for (WebElement pdfElement : pdfElements) {
            if (!pdfElement.findElements(By.xpath(xpath)).isEmpty()) {
                filteredElements.add(pdfElement);
            }
        }

        return filteredElements;
    }

    public List<WebElement> getDescendantPdfElements(List<WebElement> pdfElements) {

        List<WebElement> filteredElements = new ArrayList<>();

        for (WebElement pdfElement : pdfElements) {
            String elementText = pdfElement.getText();
            if (elementText != null && elementText.toLowerCase().contains("pdf")) {
                filteredElements.add(pdfElement);
            }
        }

        return filteredElements;
    }
}
