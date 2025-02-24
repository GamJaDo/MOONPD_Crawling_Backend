package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.PdfType;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementFilterUtil {

    public List<WebElement> getLstElementWithLink(LstType lstType, List<WebElement> lstElements) {

        String[] keyWords = {"data-idx", "data-req-get-p-idx"};

        List<WebElement> lstElementsWithLink = new ArrayList<>();

        for (WebElement lstElement : lstElements) {
            if (lstType.equals(LstType.HAS_ONCLICK)) {
                if (lstElement.getAttribute("onclick") != null) {
                    lstElementsWithLink.add(lstElement);
                }
            } else if (lstType.equals(LstType.NO_ONCLICK) || lstType.equals(LstType.JAVASCRIPT_LINK)) {
                if (lstElement.getAttribute("href") != null) {
                    lstElementsWithLink.add(lstElement);
                }
            } else if (lstType.equals(LstType.PSEUDO_LINK)) {
                String temp = "";
                for (String keyWord : keyWords) {
                    temp  = lstElement.getAttribute(keyWord);
                    if (temp != null && !temp.isEmpty()) {
                        break;
                    }
                }
                lstElementsWithLink.add(lstElement);
            } else {
                throw new WebDriverException("Unsupported lst type");
            }
        }

        return lstElementsWithLink;
    }

    public List<WebElement> getPdfElementWithLink(PdfType pdfType, List<WebElement> pdfElements) {

        List<WebElement> pdfElementsWithLink = new ArrayList<>();

        String[] keyWords = {"data-idx", "data-req-get-p-idx"};

        for (WebElement lstElement : pdfElements) {
            if (pdfType.equals(PdfType.HAS_ONCLICK)) {
                if (lstElement.getAttribute("onclick") != null) {
                    pdfElementsWithLink.add(lstElement);
                }
            } else if (pdfType.equals(PdfType.NO_ONCLICK) || pdfType.equals(PdfType.JAVASCRIPT_LINK)) {
                if (lstElement.getAttribute("href") != null) {
                    pdfElementsWithLink.add(lstElement);
                }
            } else if (pdfType.equals(PdfType.PSEUDO_LINK)) {
                String temp = "";
                for (String keyWord : keyWords) {
                    temp  = lstElement.getAttribute(keyWord);
                    if (temp != null && !temp.isEmpty()) {
                        break;
                    }
                }
                pdfElementsWithLink.add(lstElement);
            } else {
                throw new WebDriverException("Unsupported pdf type");
            }
        }

        return pdfElementsWithLink;
    }
}
