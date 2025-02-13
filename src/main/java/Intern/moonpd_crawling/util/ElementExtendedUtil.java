package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import org.springframework.stereotype.Component;

@Component
public class ElementExtendedUtil {

    public String getExtendedPdfElements(String cssSelector, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber) {

        String[] parts = cssSelector.split(">", 2);
        String firstPart = parts[0].trim();
        String remainder = parts.length > 1 ? " > " + parts[1].trim() : "";

        if (!firstPart.contains(":nth-child(")) {
            firstPart = firstPart + ":nth-child(" + extendedPdfOrdinalNumber + ")";
        }

        String parentSelector;
        if (!parentExtendedPdfIdentifier.isEmpty()) {
            parentSelector = parentExtendedPdfTagType + "." + parentExtendedPdfIdentifier;
        } else {
            parentSelector = "" + parentExtendedPdfTagType;
        }

        return parentSelector + " > " + firstPart + remainder;
    }
}
