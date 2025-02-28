package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class PseudoLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public PseudoLinkLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToPseudoLink(String pageUrl, Target target, LinkType lstType, ExtendedType extendedPdfType,
        String extendedPdfIdentifier, TagType extendedPdfTagType, SelectorType extendedPdfSelectorType,
        WebElement pseudoLinkElement, LinkType pdfType, String parentPdfIdentifier, TagType parentPdfTagType,
        SelectorType parentPdfSelectorType, String childPdfIdentifier, TagType childPdfTagType,
        SelectorType childPdfSelectorType, int pdfOrdinalNumber, String parentTitleIdentifier,
        TagType parentTitleTagType, SelectorType parentTitleSelectorType, String childTitleIdentifier,
        TagType childTitleTagType, SelectorType childTitleSelectorType, int titleOrdinalNumber) {

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
