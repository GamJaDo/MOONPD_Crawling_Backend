package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public NoOnClickLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToLstByHref(String pageUrl, Target target, LinkType lstType, ExtendedType extendedPdfType,
        String extendedPdfIdentifier, TagType extendedPdfTagType, SelectorType extendedPdfSelectorType,
        String lstLink, LinkType pdfType, String parentPdfIdentifier, TagType parentPdfTagType,
        SelectorType parentPdfSelectorType, String childPdfIdentifier, TagType childPdfTagType,
        SelectorType childPdfSelectorType, int pdfOrdinalNumber, String parentTitleIdentifier,
        TagType parentTitleTagType, SelectorType parentTitleSelectorType, String childTitleIdentifier,
        TagType childTitleTagType, SelectorType childTitleSelectorType, int titleOrdinalNumber) {

        lstCrawlingService.crawlLst(pageUrl, target, lstType, extendedPdfType,
            extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
            childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier,
            parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
    }
}
