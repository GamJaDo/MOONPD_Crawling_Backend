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
public class ListedContentStructureUtil {

    private final CrawlConfluenceUtil crawlConfluenceUtil;
    private final ElementFinderUtil elementFinderUtil;
    private final ElementCountUtil elementCountUtil;
    private final ElementLinkExtractorUtil elementLinkExtractorUtil;

    public ListedContentStructureUtil(CrawlConfluenceUtil crawlConfluenceUtil, ElementFinderUtil elementFinderUtil,
        ElementCountUtil elementCountUtil, ElementLinkExtractorUtil elementLinkExtractorUtil) {
        this.crawlConfluenceUtil = crawlConfluenceUtil;
        this.elementFinderUtil = elementFinderUtil;
        this.elementCountUtil = elementCountUtil;
        this.elementLinkExtractorUtil = elementLinkExtractorUtil;
    }

    public void crawl(WebDriver webDriver, StructureType structureType, String pageUrl, int totalPage,
        Target target, ExtendedType extendedLstType, String extendedLstIdentifier, TagType extendedLstTagType,
        SelectorType extendedLstSelectorType, LinkType lstType, String parentLstIdentifier,
        TagType parentLstTagType, SelectorType parentLstSelectorType, String childLstIdentifier,
        TagType childLstTagType, SelectorType childLstSelectorType, int lstOrdinalNumber,
        ExtendedType extendedPdfType, String extendedPdfIdentifier, TagType extendedPdfTagType,
        SelectorType extendedPdfSelectorType, LinkType pdfType, String parentPdfIdentifier,
        TagType parentPdfTagType, SelectorType parentPdfSelectorType, String childPdfIdentifier,
        TagType childPdfTagType, SelectorType childPdfSelectorType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, TagType parentTitleTagType, SelectorType parentTitleSelectorType,
        String childTitleIdentifier, TagType childTitleTagType, SelectorType childTitleSelectorType,
        int titleOrdinalNumber, LinkType nextPageType, String parentNextPageIdentifier,
        TagType parentNextPageTagType, SelectorType parentNextPageSelectorType,
        String childNextPageIdentifier, TagType childNextPageTagType, SelectorType childNextPageSelectorType,
        int nextPageOrdinalNumber) {

        List<WebElement> lstElements = null;
        List<WebElement> titleElements = null;
        String titleText = null;

        List<WebElement> nextPageElements = elementFinderUtil.getNextPageElements(webDriver,
            nextPageType, parentNextPageIdentifier, parentNextPageTagType,
            parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
            childNextPageSelectorType, nextPageOrdinalNumber);

        List<Map<String, Integer>> nextPageLinks = elementLinkExtractorUtil.getNextPageLinks(totalPage,
            nextPageType, nextPageElements);

        try {
            if (structureType.equals(StructureType.YEAR_FILTERED)) {
                totalPage = elementCountUtil.getTotalPageCnt(nextPageLinks);
            }

            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                lstElements = elementFinderUtil.getLstElements(webDriver, extendedLstType,
                    extendedLstIdentifier, extendedLstTagType, extendedLstSelectorType, lstType,
                    parentLstIdentifier, parentLstTagType, parentLstSelectorType,
                    childLstIdentifier, childLstTagType, childLstSelectorType, lstOrdinalNumber);
                if (lstElements.isEmpty()) {
                    throw new WebDriverException(
                        "No LstElements found for identifier: " + parentLstIdentifier + " or "
                            + childLstIdentifier);
                }

                if (titleType.equals(TitleType.OUT)) {
                    titleElements = elementFinderUtil.getTitleElements(webDriver, lstType, titleType,
                        parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
                }
/*
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
                for (int i=0; i<lstElements.size(); i++){
                    System.out.println("lstElements[" + i + "]: " + lstElements.get(i).getAttribute("onclick"));
                }
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

                System.out.println("------------------------------");

                System.out.println("#########################");
                for (int i=0; i<titleElements.size(); i++){
                    System.out.println("titleElements[" + i + "]: " + titleElements.get(i).getText());
                }
                System.out.println("#########################");
*/
                for (int i = 0; i < lstElements.size(); i++) {
                    if (titleType.equals(TitleType.OUT)) {
                        titleText = titleElements.get(i).getText();
                    }

                    crawlConfluenceUtil.crawlLst(webDriver, pageUrl, target, lstType, lstElements,
                        extendedPdfType, extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                        pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                        childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                        titleType, parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType, titleOrdinalNumber,
                        titleText, i);
                }

                if (currentPage < totalPage) {

                    String nextPageLink = nextPageLinks.get(currentPage).entrySet().iterator().next()
                        .getKey();

                    crawlConfluenceUtil.crawlNextPage(webDriver, nextPageType, nextPageLink);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
