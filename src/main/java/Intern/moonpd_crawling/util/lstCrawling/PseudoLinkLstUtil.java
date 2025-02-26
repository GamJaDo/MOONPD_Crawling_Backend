package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class PseudoLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public PseudoLinkLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToPseudoLink(String pageUrl, Target target, LstType lstType, ExtendedPdfType extendedPdfType,
        String extendedPdfIdentifier, ExtendedPdfTagType extendedPdfTagType,
        ExtendedPdfSelectorType extendedPdfSelectorType, WebElement pseudoLinkElement, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType,
        int pdfOrdinalNumber, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, ParentTitleSelectorType parentTitleSelectorType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType,
        ChildTitleSelectorType childTitleSelectorType, int titleOrdinalNumber) {

        String lstLink = getLstLink(pageUrl, pseudoLinkElement);


        lstCrawlingService.crawlLst(pageUrl, target, lstType, extendedPdfType,
            extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
            lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
            childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
            parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
    }

    private String getLstLink(String pageUrl, WebElement pseudoLinkElement) {

        String[] pSudoKeyWords = {"data-idx", "data-req-get-p-idx"};

        try {
            String dataIdx = null;
            for (String pSudoKeyWord : pSudoKeyWords) {
                dataIdx = pseudoLinkElement.getAttribute(pSudoKeyWord);
                if (dataIdx != null && !dataIdx.trim().isEmpty()) {
                    break;
                }
            }

            if (dataIdx == null || dataIdx.trim().isEmpty()) {
                throw new WebDriverException("Neither idx attribute is present in pseudo link element.");
            }

            String viewUrl = pageUrl.replace("list.do", "view.do");
            if (pageUrl.contains("?")) {
                return viewUrl + "&idx=" + dataIdx;
            } else {
                return viewUrl + "?idx=" + dataIdx;
            }
        } catch (Exception e) {
            throw new WebDriverException("Failed to navigate to view URL using pseudo link element.", e);
        }
    }

}
