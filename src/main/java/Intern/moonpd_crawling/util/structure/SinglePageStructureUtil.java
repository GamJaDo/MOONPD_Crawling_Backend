package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.util.CrawlPdfConfluenceUtil;
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
public class SinglePageStructureUtil {

    private final CrawlingDataRepository crawlingDataRepository;
    private final CrawlConfluenceUtil crawlConfluenceUtil;
    private final CrawlPdfConfluenceUtil crawlPdfConfluenceUtil;
    private final ElementFinderUtil elementFinderUtil;
    private final ElementCountUtil elementCountUtil;
    private final ElementLinkExtractorUtil elementLinkExtractorUtil;

    public SinglePageStructureUtil(CrawlingDataRepository crawlingDataRepository,
        CrawlConfluenceUtil crawlConfluenceUtil, CrawlPdfConfluenceUtil crawlPdfConfluenceUtil,
        ElementFinderUtil elementFinderUtil, ElementCountUtil elementCountUtil,
        ElementLinkExtractorUtil elementLinkExtractorUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.crawlConfluenceUtil = crawlConfluenceUtil;
        this.crawlPdfConfluenceUtil = crawlPdfConfluenceUtil;
        this.elementFinderUtil = elementFinderUtil;
        this.elementCountUtil = elementCountUtil;
        this.elementLinkExtractorUtil = elementLinkExtractorUtil;
    }

    public void crawl(WebDriver webDriver, StructureType structureType, String pageUrl,
        int totalPage, Target target, LinkType lstType, ExtendedType extendedPdfType,
        String extendedPdfIdentifier, TagType extendedPdfTagType, SelectorType extendedPdfSelectorType,
        LinkType pdfType, String parentPdfIdentifier, TagType parentPdfTagType,
        SelectorType parentPdfSelectorType, String childPdfIdentifier, TagType childPdfTagType,
        SelectorType childPdfSelectorType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, TagType parentTitleTagType, SelectorType parentTitleSelectorType,
        String childTitleIdentifier, TagType childTitleTagType, SelectorType childTitleSelectorType,
        int titleOrdinalNumber, LinkType nextPageType, String parentNextPageIdentifier,
        TagType parentNextPageTagType, SelectorType parentNextPageSelectorType,
        String childNextPageIdentifier, TagType childNextPageTagType, SelectorType childNextPageSelectorType,
        int nextPageOrdinalNumber) {

        List<WebElement> titleElements = null;
        List<WebElement> pdfElements = null;

        List<WebElement> nextPageElements = elementFinderUtil.getNextPageElements(webDriver,
            nextPageType, parentNextPageIdentifier, parentNextPageTagType,
            parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
            childNextPageSelectorType, nextPageOrdinalNumber);

        List<Map<String, Integer>> nextPageLinks = elementLinkExtractorUtil.getNextPageLinks(totalPage,
            nextPageType, nextPageElements);
/*
        System.out.println("######################");
        for (int i = 0; i < nextPageLinks.size(); i++) {
            System.out.println("nextPageLink[" + i + "]: " + nextPageLinks.get(i).entrySet().iterator().next().getKey());
        }
        System.out.println("######################");
*/
        try {
            if (structureType.equals(StructureType.YEAR_FILTERED)) {
                totalPage = elementCountUtil.getTotalPageCnt(nextPageLinks);
            }

            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                pdfElements = elementFinderUtil.getPdfElements(webDriver, lstType, extendedPdfType,
                    extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, pdfType,
                    parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
                    childPdfTagType, childPdfSelectorType, pdfOrdinalNumber);

                /*
                if (pdfLinks.isEmpty()) {
                    throw new WebDriverException(
                        "No PDF links found for identifier: " + parentPdfIdentifier + " or "
                            + childPdfIdentifier);
                }
                */
                titleElements = elementFinderUtil.getTitleElements(webDriver, lstType, titleType,
                    parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
                    childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
                /*
                if (titles.isEmpty()) {
                    throw new WebDriverException(
                        "No titles found for identifier: " + parentTitleIdentifier + " or "
                            + childTitleIdentifier);
                }
                */
/*
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
                for (int i=0; i<pdfElements.size(); i++){
                    System.out.println("pdfElements[" + i + "]: " + pdfElements.get(i).getAttribute("href"));
                }
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

                System.out.println("------------------------------");

                System.out.println("#########################");
                for (int i=0; i<titleElements.size(); i++){
                    System.out.println("titleElements[" + i + "]: " + titleElements.get(i).getText());
                }
                System.out.println("#########################");
*/
                if (pdfElements.size() != titleElements.size()) {
                    int diff = Math.abs(pdfElements.size() - titleElements.size());

                    if (pdfElements.size() > titleElements.size()) {
                        pdfElements = pdfElements.subList(diff, pdfElements.size());
                    } else {
                        titleElements = titleElements.subList(0, titleElements.size() - diff);
                    }
                }

                for (int i = 0; i < pdfElements.size(); i++) {

                    String pdfLink = crawlPdfConfluenceUtil.crawlPdf(pageUrl, pdfType, pdfElements, i);
                    String titleText = titleElements.get(i).getText();

                    if (crawlingDataRepository.existsByPdfUrl(pdfLink)) {
                        continue;
                    }

                    CrawlingData crawlingData = new CrawlingData(target, pdfLink, titleText);
                    crawlingData.setCrawlingTime();

                    crawlingDataRepository.save(crawlingData);
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
