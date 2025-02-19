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

        List<WebElement> titles = null;
        List<WebElement> pdfLinks = null;

        List<WebElement> nextPageLinks = elementFinderUtil.getNextPageElements(webDriver,
            parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
            childNextPageTagType, nextPageOrdinalNumber);

        try {
            int totalPage = elementCountUtil.getTotalPageCnt(webDriver, parentNextPageIdentifier,
                parentNextPageTagType, childNextPageIdentifier, childNextPageTagType);

            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                pdfLinks = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
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
                titles = elementFinderUtil.getTitleElements(webDriver, parentTitleIdentifier,
                    parentTitleTagType,
                    childTitleIdentifier, childTitleTagType, titleOrdinalNumber);
                /*
                if (titles.isEmpty()) {
                    throw new WebDriverException(
                        "No titles found for identifier: " + parentTitleIdentifier + " or "
                            + childTitleIdentifier);
                }
                */

                if (pdfLinks.size() != titles.size()) {
                    int diff = Math.abs(pdfLinks.size() - titles.size());

                    if (pdfLinks.size() > titles.size()) {
                        pdfLinks = pdfLinks.subList(diff, pdfLinks.size());
                    } else {
                        titles = titles.subList(0, titles.size() - diff);
                    }
                }

                for (int i = 0; i < pdfLinks.size(); i++) {

                    String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl,
                        pdfType, pdfLinks, childPdfTagType, i);
                    String titleText = titles.get(i).getText();

                    if (crawlingDataRepository.existsByPdfUrl(pdfLink)) {
                        continue;
                    }

                    CrawlingData crawlingData = new CrawlingData(target, pdfLink, titleText);
                    crawlingData.setCrawlingTime();

                    crawlingDataRepository.save(crawlingData);
                }

                if (currentPage < totalPage) {
                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType, nextPageLinks,
                        currentPage);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
