package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickPdfUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class LstCrawlingService {

    private final CrawlingDataRepository crawlingDataRepository;
    private final CheckOnClickPdfUtil checkOnClickPdfUtil;
    private final ElementFinderUtil elementFinderUtil;

    public LstCrawlingService(CrawlingDataRepository crawlingDataRepository,
        CheckOnClickPdfUtil checkOnClickPdfUtil, ElementFinderUtil elementFinderUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.checkOnClickPdfUtil = checkOnClickPdfUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void crawlLstWithTitleText(String pageUrl, Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, String lstLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, ParentPdfSelectorType parentPdfSelectorType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber, String titleText) {

        System.setProperty("webdriver.chrome.driver",
            "C:\\tools\\chromedriver-win64\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();

        List<WebElement> pdfElements = null;

        try {
            webDriver.get(lstLink);
            Thread.sleep(500);

            pdfElements = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber);

            if (!pdfElements.isEmpty()) {
                for (int i = 0; i < pdfElements.size(); i++) {
                    String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl,
                        pdfType,
                        pdfElements, childPdfTagType, i);
                    System.out.println("################");
                    System.out.println("pdfLink: " + pdfLink);
                    System.out.println("################");
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

    public void crawlLst(String pageUrl, Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, String lstLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, ParentPdfSelectorType parentPdfSelectorType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber) {

        System.setProperty("webdriver.chrome.driver",
            "C:\\tools\\chromedriver-win64\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();

        List<WebElement> pdfElements = null;
        List<WebElement> titleElements = null;

        try {
            webDriver.get(lstLink);
            Thread.sleep(500);

            pdfElements = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber);
            titleElements = elementFinderUtil.getTitleElements(webDriver, parentTitleIdentifier,
                parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
                childTitleTagType, childTitleSelectorType, titleOrdinalNumber);

            if (!pdfElements.isEmpty()) {
                for (int i = 0; i < pdfElements.size(); i++) {
                    String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl,
                        pdfType,
                        pdfElements, childPdfTagType, i);
                    String titleText = titleElements.get(i).getText();

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
