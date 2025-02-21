package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class PseudoLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public PseudoLinkLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToPseudoLink(String pageUrl, Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, WebElement pseudoLinkElement, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        String titleText) {

        String lstLink = getLstLink(pageUrl, pseudoLinkElement);

        if (titleType.equals(TitleType.OUT)) {
            lstCrawlingService.crawlLstWithTitle(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                childPdfTagType, pdfOrdinalNumber, titleText);
        } else if (titleType.equals(TitleType.IN)) {
            lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier,
                parentExtendedPdfTagType, extendedPdfOrdinalNumber, lstLink, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
                childTitleTagType, titleOrdinalNumber);
        }
    }

    private String getLstLink(String pageUrl, WebElement pseudoLinkElement) {
        try {
            String dataIdx = pseudoLinkElement.getAttribute("data-idx");
            if (dataIdx == null || dataIdx.trim().isEmpty()) {
                throw new WebDriverException(
                    "data-idx attribute is missing in pseudo link element.");
            }

            String viewUrl = pageUrl.replace("list.do", "view.do");

            if (pageUrl.contains("?")) {
                return viewUrl + "&idx=" + dataIdx;
            } else {
                return viewUrl + "?idx=" + dataIdx;
            }
        } catch (Exception e) {
            throw new WebDriverException(
                "Failed to navigate to view URL using pseudo link element.", e);
        }
    }
}
