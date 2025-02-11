package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class LstCrawlingService {

    private final CrawlingDataRepository crawlingDataRepository;
    private final ElementFinderUtil elementFinderUtil;

    public LstCrawlingService(CrawlingDataRepository crawlingDataRepository,
        ElementFinderUtil elementFinderUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void crawlLst(Target target, String lstLink, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        int pdfOrdinalNumber, String titleText) {

        System.setProperty("webdriver.chrome.driver",
            "C:\\tools\\chromedriver-win64\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();

        List<WebElement> pdfLinks = null;

        try {
            webDriver.get(lstLink);
            Thread.sleep(500);

            pdfLinks = elementFinderUtil.getPdfElements(webDriver, parentPdfIdentifier,
                parentPdfTagType,
                childPdfIdentifier, childPdfTagType, pdfOrdinalNumber);
            if (pdfLinks.isEmpty()) {
                throw new WebDriverException(
                    "No PDF links found for identifier: " + parentPdfIdentifier + " or "
                        + childPdfIdentifier);
            }

            for (int i = 0; i < pdfLinks.size(); i++) {
                String pdfLink = elementFinderUtil.getPdfLink(webDriver, pdfLinks, childPdfTagType,
                    i);

                if (crawlingDataRepository.existsByPdfUrl(pdfLink)) {
                    continue;
                }

                CrawlingData crawlingData = new CrawlingData(target, pdfLink, titleText);
                crawlingData.setCrawlingTime();

                crawlingDataRepository.save(crawlingData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        } finally {
            webDriver.quit();
        }
    }
}
