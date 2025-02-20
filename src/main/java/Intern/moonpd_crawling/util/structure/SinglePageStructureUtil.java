package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickPdfUtil;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import Intern.moonpd_crawling.util.ElementCountUtil;
import java.util.ArrayList;
import java.util.List;
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

    public void crawl(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType, int nextPageOrdinalNumber) {

        List<WebElement> titleElements = null;
        List<WebElement> pdfElements = null;

        List<WebElement> nextPageElements = elementFinderUtil.getNextPageElements(webDriver,
            parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
            childNextPageTagType, nextPageOrdinalNumber);
        if (nextPageElements.isEmpty()) {
            throw new WebDriverException(
                "No NextPageElement found for identifier: " + parentNextPageIdentifier + " or "
                    + childNextPageIdentifier);
        }

        List<String> nextPageLinks = new ArrayList<>();
        for (WebElement nextPageElement : nextPageElements) {
            nextPageLinks.add(nextPageElement.getAttribute("href"));
        }

        try {
            int totalPage = elementCountUtil.getTotalPageCnt(webDriver, parentNextPageIdentifier,
                parentNextPageTagType, childNextPageIdentifier, childNextPageTagType,
                nextPageOrdinalNumber);

            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                pdfElements = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
                    parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                    parentPdfIdentifier, parentPdfTagType,
                    childPdfIdentifier, childPdfTagType, pdfOrdinalNumber);
                /*
                if (pdfLinks.isEmpty()) {
                    throw new WebDriverException(
                        "No PDF links found for identifier: " + parentPdfIdentifier + " or "
                            + childPdfIdentifier);
                }
                */
                titleElements = elementFinderUtil.getTitleElements(webDriver, parentTitleIdentifier,
                    parentTitleTagType,
                    childTitleIdentifier, childTitleTagType, titleOrdinalNumber);
                /*
                if (titles.isEmpty()) {
                    throw new WebDriverException(
                        "No titles found for identifier: " + parentTitleIdentifier + " or "
                            + childTitleIdentifier);
                }
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
                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType, nextPageLinks.get(currentPage));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
