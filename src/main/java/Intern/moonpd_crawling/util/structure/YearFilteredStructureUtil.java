package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.util.CrawlConfluenceUtil;
import Intern.moonpd_crawling.util.ElementCountUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import Intern.moonpd_crawling.util.ElementLinkExtractorUtil;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class YearFilteredStructureUtil {

    private final SinglePageStructureUtil singlePageStructureUtil;
    private final ListedContentStructureUtil listedContentStructureUtil;
    private final CrawlConfluenceUtil crawlConfluenceUtil;
    private final ElementFinderUtil elementFinderUtil;
    private final ElementCountUtil elementCountUtil;
    private final ElementLinkExtractorUtil elementLinkExtractorUtil;

    public YearFilteredStructureUtil(SinglePageStructureUtil singlePageStructureUtil,
        ListedContentStructureUtil listedContentStructureUtil, CrawlConfluenceUtil crawlConfluenceUtil,
        ElementFinderUtil elementFinderUtil, ElementCountUtil elementCountUtil,
        ElementLinkExtractorUtil elementLinkExtractorUtil) {
        this.singlePageStructureUtil = singlePageStructureUtil;
        this.listedContentStructureUtil = listedContentStructureUtil;
        this.crawlConfluenceUtil = crawlConfluenceUtil;
        this.elementFinderUtil = elementFinderUtil;
        this.elementCountUtil = elementCountUtil;
        this.elementLinkExtractorUtil = elementLinkExtractorUtil;
    }

    public void crawl(WebDriver webDriver, StructureType structureType, String pageUrl, int totalPage,
        Target target, ExtendedType extendedLstType, String extendedLstIdentifier, TagType extendedLstTagType,
        SelectorType extendedLstSelectorType, LinkType lstType, String parentLstIdentifier,
        TagType parentLstTagType, SelectorType parentLstSelectorType, String childLstIdentifier,
        TagType childLstTagType, SelectorType childLstSelectorType, int lstOrdinalNumber, LinkType yearType,
        String parentYearIdentifier, TagType parentYearTagType, SelectorType parentYearSelectorType,
        String childYearIdentifier, TagType childYearTagType, SelectorType childYearSelectorType,
        int yearOrdinalNumber, ExtendedType extendedPdfType, String extendedPdfIdentifier,
        TagType extendedPdfTagType, SelectorType extendedPdfSelectorType, LinkType pdfType,
        String parentPdfIdentifier, TagType parentPdfTagType, SelectorType parentPdfSelectorType,
        String childPdfIdentifier, TagType childPdfTagType, SelectorType childPdfSelectorType,
        int pdfOrdinalNumber, TitleType titleType, String parentTitleIdentifier, TagType parentTitleTagType,
        SelectorType parentTitleSelectorType, String childTitleIdentifier, TagType childTitleTagType,
        SelectorType childTitleSelectorType, int titleOrdinalNumber, LinkType nextPageType,
        String parentNextPageIdentifier, TagType parentNextPageTagType,
        SelectorType parentNextPageSelectorType, String childNextPageIdentifier, TagType childNextPageTagType,
        SelectorType childNextPageSelectorType, int nextPageOrdinalNumber) throws InterruptedException {

        List<WebElement> yearElements = elementFinderUtil.getYearElements(webDriver, yearType,
            parentYearIdentifier, parentYearTagType, parentYearSelectorType, childYearIdentifier,
            childYearTagType, childYearSelectorType, yearOrdinalNumber);
        if (yearElements.isEmpty()) {
            throw new WebDriverException(
                "No YearElement found for identifier: " + parentYearIdentifier + " or "
                    + childYearIdentifier);
        }

        List<Map<String, Integer>> yearLinks = elementLinkExtractorUtil.getYearLinks(pageUrl, yearType,
            yearElements);

        System.out.println("############################");
        for (int i = 0; i < yearElements.size(); i++) {
            System.out.println("yearLinks[" + i + "]: " + yearLinks.get(i));
        }
        System.out.println("############################");

        try {
            int totalYear = elementCountUtil.getTotalYearCnt(yearLinks);

            for (int currentYear = 1; currentYear <= totalYear; currentYear++) {
                Thread.sleep(500);

                if (childLstTagType.equals(TagType.NONE)) {
                    singlePageStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, lstType, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
                        extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                        pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
                        parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                        childTitleSelectorType, titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
                } else {
                    listedContentStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, extendedLstType, extendedLstIdentifier, extendedLstTagType,
                        extendedLstSelectorType, lstType, parentLstIdentifier, parentLstTagType,
                        parentLstSelectorType, childLstIdentifier, childLstTagType, childLstSelectorType,
                        lstOrdinalNumber, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
                        extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                        pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
                        parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                        childTitleSelectorType, titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
                }

                if (currentYear < totalYear) {

                    String yearLink = yearLinks.get(currentYear).entrySet().iterator().next().getKey();

                    crawlConfluenceUtil.crawlYear(webDriver, yearType, yearLink);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
