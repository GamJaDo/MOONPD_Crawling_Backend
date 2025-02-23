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

    public List<WebElement> getExtendedLstElements(
        ParentExtendedLstTagType parentExtendedLstTagType, String parentExtendedLstIdentifier,
        List<WebElement> lstElements) {

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
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        List<WebElement> pdfElements) {

        String xpath = "ancestor::*[local-name()='" + parentExtendedPdfTagType.toString() + "'";
        if (parentExtendedPdfIdentifier != null && !parentExtendedPdfIdentifier.isEmpty()) {
            xpath += " and contains(@class, '" + parentExtendedPdfIdentifier + "')";
        }
        xpath += "]";

        WebElement parentExtended = pdfElements.get(0).findElement(By.xpath(xpath));

        String childTag = pdfElements.get(0).getTagName();
        String childSelector = childTag + ":nth-child(" + extendedPdfOrdinalNumber + ")";

        List<WebElement> extendedElements = parentExtended.findElements(By.cssSelector(childSelector));

        return extendedElements;
    }

}
