package Intern.moonpd_crawling.util;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementFinderFilterUtil {

    public List<WebElement> getLstElementWithLink(List<WebElement> lstElements) {

        List<WebElement> lstElementsWithLink = new ArrayList<>();

        for (WebElement lstElement : lstElements) {
            if (lstElement.getAttribute("href") != null
                || lstElement.getAttribute("onclick") != null) {
                lstElementsWithLink.add(lstElement);
            }
        }

        return lstElementsWithLink;
    }

    public List<WebElement> getPdfElementWithLink(List<WebElement> pdfElements) {

        List<WebElement> pdfElementsWithLink = new ArrayList<>();

        for (WebElement pdfElement : pdfElements) {
            if (pdfElement.getAttribute("href") != null
                || pdfElement.getAttribute("onclick") != null) {
                pdfElementsWithLink.add(pdfElement);
            }
        }

        return pdfElementsWithLink;
    }
}
