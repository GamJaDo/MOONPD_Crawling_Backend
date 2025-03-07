package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.CrawlDetailPageService;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import org.springframework.stereotype.Component;

@Component
public class HrefLinkLstUtil {

    private final CrawlDetailPageService crawlDetailPageService;

    public HrefLinkLstUtil(CrawlDetailPageService crawlDetailPageService) {
        this.crawlDetailPageService = crawlDetailPageService;
    }

    public void goToLstByHref(String pageUrl, Target target, LinkType lstType, ExtendedType extendedPdfType,
        String extendedPdfIdentifier, TagType extendedPdfTagType, SelectorType extendedPdfSelectorType,
        String lstLink, LinkType pdfType, String parentPdfIdentifier, TagType parentPdfTagType,
        SelectorType parentPdfSelectorType, String childPdfIdentifier, TagType childPdfTagType,
        SelectorType childPdfSelectorType, int pdfOrdinalNumber, ExtendedType extendedTitleType,
        String extendedTitleIdentifier, TagType extendedTitleTagType, SelectorType extendedTitleSelectorType,
        TitleType titleType, String parentTitleIdentifier, TagType parentTitleTagType,
        SelectorType parentTitleSelectorType, String childTitleIdentifier, TagType childTitleTagType,
        SelectorType childTitleSelectorType, int titleOrdinalNumber, String titleText) {

        crawlDetailPageService.crawlSubPage(pageUrl, target, lstType, extendedPdfType,
            extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
            childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, extendedTitleType,
            extendedTitleIdentifier, extendedTitleTagType, extendedTitleSelectorType, titleType,
            parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber, titleText);
    }
}
