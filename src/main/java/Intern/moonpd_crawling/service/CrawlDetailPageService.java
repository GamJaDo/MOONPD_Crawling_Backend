package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.util.CrawlPdfConfluenceUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class CrawlDetailPageService {

    private final CrawlingDataRepository crawlingDataRepository;
    private final CrawlPdfConfluenceUtil crawlPdfConfluenceUtil;
    private final ElementFinderUtil elementFinderUtil;

    public CrawlDetailPageService(CrawlingDataRepository crawlingDataRepository,
        CrawlPdfConfluenceUtil crawlPdfConfluenceUtil, ElementFinderUtil elementFinderUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.crawlPdfConfluenceUtil = crawlPdfConfluenceUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void crawlSubPage(String pageUrl, Target target, LinkType lstType, ExtendedType extendedPdfType,
        String extendedPdfIdentifier, TagType extendedPdfTagType, SelectorType extendedPdfSelectorType,
        String lstLink, LinkType pdfType, String parentPdfIdentifier, TagType parentPdfTagType,
        SelectorType parentPdfSelectorType, String childPdfIdentifier, TagType childPdfTagType,
        SelectorType childPdfSelectorType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, TagType parentTitleTagType, SelectorType parentTitleSelectorType,
        String childTitleIdentifier, TagType childTitleTagType, SelectorType childTitleSelectorType,
        int titleOrdinalNumber, String titleText) {

        System.setProperty("webdriver.chrome.driver",
            "C:\\tools\\chromedriver-win64\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();

        List<WebElement> pdfElements = null;
        List<WebElement> titleElements = null;

        try {
            webDriver.get(lstLink);
            Thread.sleep(500);

            pdfElements = elementFinderUtil.getPdfElements(webDriver, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                pdfType, parentPdfIdentifier,
                parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                childPdfSelectorType, pdfOrdinalNumber);

            if (!titleType.equals(TitleType.OUT)) {
                titleElements = elementFinderUtil.getTitleElements(webDriver, lstType, titleType,
                    parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
                    childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
            }

            if (!pdfElements.isEmpty()) {
                for (int i = 0; i < pdfElements.size(); i++) {
                    String pdfLink = crawlPdfConfluenceUtil.crawlPdf(pageUrl, pdfType, pdfElements, i);

                    if (!titleType.equals(TitleType.OUT)) {
                        titleText = titleElements.get(i).getText();
                    }

                    if (crawlingDataRepository.existsByPdfUrl(pdfLink)) {
                        continue;
                    }

                    CrawlingData crawlingData = new CrawlingData(target, pdfLink, titleText);
                    crawlingData.setCrawlingTime();

                    crawlingDataRepository.save(crawlingData);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        } finally {
            webDriver.quit();
        }
    }
}
