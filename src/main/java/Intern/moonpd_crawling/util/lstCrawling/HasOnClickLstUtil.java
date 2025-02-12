package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickLstUtil {

    private final CrawlingDataRepository crawlingDataRepository;
    private final ElementFinderUtil elementFinderUtil;

    public HasOnClickLstUtil(CrawlingDataRepository crawlingDataRepository,
        ElementFinderUtil elementFinderUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void goToLstByOnclick(WebDriver webDriver, Target target, String onClickLstScript,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, int pdfOrdinalNumber,
        String titleText) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(onClickLstScript);

        } catch (Exception e) {
            throw new WebDriverException("Failed to execute onclick script: " + onClickLstScript);
        }

        List<WebElement> pdfLinks = elementFinderUtil.getPdfElements(webDriver, parentPdfIdentifier,
            parentPdfTagType, childPdfIdentifier, childPdfTagType, pdfOrdinalNumber);

        if (!pdfLinks.isEmpty()) {
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
        }
    }
}
