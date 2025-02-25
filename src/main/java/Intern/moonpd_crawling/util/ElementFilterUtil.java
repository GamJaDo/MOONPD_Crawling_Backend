package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.constants.KeywordConstants;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.PdfType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ElementFilterUtil {

    public List<WebElement> getLstElementWithLink(LstType lstType, List<WebElement> lstElements) {

        List<WebElement> lstElementsWithLink = new ArrayList<>();

        for (WebElement lstElement : lstElements) {
            if (lstType.equals(LstType.HAS_ONCLICK)) {
                String onClickAttr = lstElement.getAttribute("onclick");
                if (onClickAttr != null) {
                    boolean flag = false;
                    for (String onClickLstKeyWord : KeywordConstants.ON_CLICK_LST_KEYWORDS) {
                        if (onClickAttr.contains(onClickLstKeyWord)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        lstElementsWithLink.add(lstElement);
                    }
                }
            } else if (lstType.equals(LstType.NO_ONCLICK) || lstType.equals(LstType.JAVASCRIPT_LINK)) {
                if (lstElement.getAttribute("href") != null) {
                    lstElementsWithLink.add(lstElement);
                }
            } else if (lstType.equals(LstType.PSEUDO_LINK)) {
                String temp = "";
                for (String pSudoKeyWord : KeywordConstants.P_SUDO_KEYWORDS) {
                    temp = lstElement.getAttribute(pSudoKeyWord);
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

        for (WebElement pdfElement : pdfElements) {
            if (pdfType.equals(PdfType.HAS_ONCLICK)) {
                String onClickAttr = pdfElement.getAttribute("onclick");
                if (onClickAttr != null) {
                    boolean flag = false;
                    for (String onClickPdfKeyWord : KeywordConstants.ON_CLICK_PDF_KEYWORDS) {
                        if (onClickAttr.contains(onClickPdfKeyWord)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        pdfElementsWithLink.add(pdfElement);
                    }
                }
            } else if (pdfType.equals(PdfType.NO_ONCLICK) || pdfType.equals(PdfType.JAVASCRIPT_LINK)) {
                if (pdfElement.getAttribute("href") != null) {
                    pdfElementsWithLink.add(pdfElement);
                }
            } else if (pdfType.equals(PdfType.PSEUDO_LINK)) {
                String temp = "";
                for (String pSudoKeyWord : KeywordConstants.P_SUDO_KEYWORDS) {
                    temp = pdfElement.getAttribute(pSudoKeyWord);
                    if (temp != null && !temp.isEmpty()) {
                        break;
                    }
                }
                pdfElementsWithLink.add(pdfElement);
            } else {
                throw new WebDriverException("Unsupported pdf type");
            }
        }

        return pdfElementsWithLink;
    }
}
