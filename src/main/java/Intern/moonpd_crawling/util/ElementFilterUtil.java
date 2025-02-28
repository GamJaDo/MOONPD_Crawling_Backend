package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.constants.KeywordConstants;
import Intern.moonpd_crawling.status.type.LinkType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ElementFilterUtil {

    public List<WebElement> getLstElementWithLink(LinkType lstType, List<WebElement> lstElements) {

        String[] onClickLstKeywords =  KeywordConstants.ON_CLICK_LST_KEYWORDS;
        List<WebElement> lstElementsWithLink = new ArrayList<>();

        for (WebElement lstElement : lstElements) {
            if (lstType.equals(LinkType.ONCLICK_LINK)) {
                String onClickAttr = lstElement.getAttribute("onclick");
                if (onClickAttr != null) {
                    boolean flag = false;
                    for (String onClickLstKeyWord : onClickLstKeywords) {
                        if (onClickAttr.contains(onClickLstKeyWord)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        lstElementsWithLink.add(lstElement);
                    }
                }
            } else if (lstType.equals(LinkType.HREF_LINK) || lstType.equals(LinkType.JAVASCRIPT_LINK)) {
                if (lstElement.getAttribute("href") != null) {
                    lstElementsWithLink.add(lstElement);
                }
            } else if (lstType.equals(LinkType.PSEUDO_LINK)) {
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

    public List<WebElement> getPdfElementWithLink(LinkType pdfType, List<WebElement> pdfElements) {

        String[] onClickPdfKeywords = KeywordConstants.ON_CLICK_PDF_KEYWORDS;
        List<WebElement> pdfElementsWithLink = new ArrayList<>();

        for (WebElement pdfElement : pdfElements) {
            if (pdfType.equals(LinkType.ONCLICK_LINK)) {
                String onClickAttr = pdfElement.getAttribute("onclick");
                if (onClickAttr != null) {
                    boolean flag = false;
                    for (String onClickPdfKeyWord : onClickPdfKeywords) {
                        if (onClickAttr.contains(onClickPdfKeyWord)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        pdfElementsWithLink.add(pdfElement);
                    }
                }
            } else if (pdfType.equals(LinkType.HREF_LINK) || pdfType.equals(LinkType.JAVASCRIPT_LINK)) {
                if (pdfElement.getAttribute("href") != null) {
                    pdfElementsWithLink.add(pdfElement);
                }
            } else if (pdfType.equals(LinkType.PSEUDO_LINK)) {
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
/*
        System.out.println("###################");
        for (int i = 0; i < pdfElementsWithLink.size(); i++) {
            System.out.println("pdfElementsWithLink[" + i + "]: " + pdfElementsWithLink.get(i).getAttribute("onclick"));
        }
        System.out.println("###################");
*/
        return pdfElementsWithLink;
    }
}
