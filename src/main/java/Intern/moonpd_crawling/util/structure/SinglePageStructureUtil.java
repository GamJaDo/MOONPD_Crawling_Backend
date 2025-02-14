package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickPdfUtil;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
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

    public SinglePageStructureUtil(CrawlingDataRepository crawlingDataRepository,
        CheckOnClickUtil checkOnClickUtil, CheckOnClickPdfUtil checkOnClickPdfUtil,
        ElementFinderUtil elementFinderUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.checkOnClickUtil = checkOnClickUtil;
        this.checkOnClickPdfUtil = checkOnClickPdfUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void crawl(WebDriver webDriver, String pageUrl, Target target, int totalPage,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        NextPageType nextPageType,
        String nextIdentifier) {

        List<WebElement> titles = null;
        List<WebElement> pdfLinks = null;

        try {
            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {

                pdfLinks = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
                    parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                    parentPdfIdentifier, parentPdfTagType,
                    childPdfIdentifier, childPdfTagType, pdfOrdinalNumber);
                if (pdfLinks.isEmpty()) {
                    throw new WebDriverException(
                        "No PDF links found for identifier: " + parentPdfIdentifier + " or "
                            + childPdfIdentifier);
                }

                titles = elementFinderUtil.getTitleElements(webDriver, parentTitleIdentifier, parentTitleTagType,
                    childTitleIdentifier, childTitleTagType, titleOrdinalNumber);
                if (titles.isEmpty()) {
                    throw new WebDriverException(
                        "No titles found for identifier: " + parentTitleIdentifier + " or "
                            + childTitleIdentifier);
                }

                if (pdfLinks.size() != titles.size()) {
                    int diff = Math.abs(pdfLinks.size() - titles.size());

                    pdfLinks = pdfLinks.subList(diff, pdfLinks.size());
                }

                for (int i = 0; i < pdfLinks.size(); i++) {

                    String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl, pdfType, pdfLinks, childPdfTagType, i);
                    String titleText = titles.get(i).getText();

                    if (crawlingDataRepository.existsByPdfUrl(pdfLink)) {
                        continue;
                    }

                    CrawlingData crawlingData = new CrawlingData(target, pdfLink, titleText);
                    crawlingData.setCrawlingTime();

                    crawlingDataRepository.save(crawlingData);
                }

                if (currentPage < totalPage) {
                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType, nextIdentifier,
                        currentPage);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}

/*
                System.out.println("\n\n ###################################");
                System.out.println("currentPage: " + currentPage);
                System.out.println("pdfLinks.size(): " + pdfLinks.size());
                System.out.println("titles.size(): " + titles.size());
                System.out.println(" ###################################");
 */