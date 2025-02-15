package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class PseudoLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public PseudoLinkLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToPseudoLink(String pageUrl, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, Target target, WebElement pseudoLinkElement, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String titleText) {

        String pseudoLink;

        try {
            String dataIdx = pseudoLinkElement.getAttribute("data-idx");
            if (dataIdx == null || dataIdx.trim().isEmpty()) {
                throw new WebDriverException("data-idx attribute is missing in pseudo link element.");
            }

            String viewUrl = pageUrl.replace("list.do", "view.do");

            if (pageUrl.contains("?")) {
                pseudoLink = viewUrl + "&idx=" + dataIdx;
            } else {
                pseudoLink = viewUrl + "?idx=" + dataIdx;
            }
        } catch (Exception e) {
            throw new WebDriverException("Failed to navigate to view URL using pseudo link element.", e);
        }

        lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType, parentExtendedPdfIdentifier,
            parentExtendedPdfTagType,
            extendedPdfOrdinalNumber, pseudoLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, titleText);
    }
}
