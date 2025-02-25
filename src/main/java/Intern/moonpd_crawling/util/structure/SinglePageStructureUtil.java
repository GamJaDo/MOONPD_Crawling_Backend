package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.selector.child.ChildNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.util.CheckOnClickPdfUtil;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import Intern.moonpd_crawling.util.ElementCountUtil;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class SinglePageStructureUtil {

    private final CrawlingDataRepository crawlingDataRepository;
    private final CheckOnClickUtil checkOnClickUtil;
    private final CheckOnClickPdfUtil checkOnClickPdfUtil;
    private final ElementFinderUtil elementFinderUtil;
    private final ElementCountUtil elementCountUtil;

    public SinglePageStructureUtil(CrawlingDataRepository crawlingDataRepository,
        CheckOnClickUtil checkOnClickUtil, CheckOnClickPdfUtil checkOnClickPdfUtil,
        ElementFinderUtil elementFinderUtil, ElementCountUtil elementCountUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.checkOnClickUtil = checkOnClickUtil;
        this.checkOnClickPdfUtil = checkOnClickPdfUtil;
        this.elementFinderUtil = elementFinderUtil;
        this.elementCountUtil = elementCountUtil;
    }

    public void crawl(WebDriver webDriver, StructureType structureType, String pageUrl,
        int totalPage, Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber, NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType,
        ParentNextPageSelectorType parentNextPageSelectorType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType,
        ChildNextPageSelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {

        List<WebElement> titleElements = null;
        List<WebElement> pdfElements = null;

        List<WebElement> nextPageElements = elementFinderUtil.getNextPageElements(webDriver,
            nextPageType, parentNextPageIdentifier, parentNextPageTagType,
            parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
            childNextPageSelectorType, nextPageOrdinalNumber);

        List<Map<String, Integer>> nextPageLinks = checkOnClickUtil.getNextPageLinks(totalPage,
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

                pdfElements = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
                    parentExtendedPdfIdentifier, parentExtendedPdfTagType, pdfType, parentPdfIdentifier,
                    parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                    childPdfSelectorType, pdfOrdinalNumber);

                /*
                if (pdfLinks.isEmpty()) {
                    throw new WebDriverException(
                        "No PDF links found for identifier: " + parentPdfIdentifier + " or "
                            + childPdfIdentifier);
                }
                */
                titleElements = elementFinderUtil.getTitleElements(webDriver, parentTitleIdentifier,
                    parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
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

                    String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl,
                        pdfType, pdfElements, childPdfTagType, i);
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

                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType, nextPageLink);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
