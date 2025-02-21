package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickPdfUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptLinkLstUtil {

    private final CrawlingDataRepository crawlingDataRepository;
    private final CheckOnClickPdfUtil checkOnClickPdfUtil;
    private final ElementFinderUtil elementFinderUtil;

    public JavaScriptLinkLstUtil(CrawlingDataRepository crawlingDataRepository,
        CheckOnClickPdfUtil checkOnClickPdfUtil, ElementFinderUtil elementFinderUtil) {
        this.crawlingDataRepository = crawlingDataRepository;
        this.checkOnClickPdfUtil = checkOnClickPdfUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void goToJavaScriptLinkWithTitle(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String javaScriptLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String titleText) {

        javascriptLinkExecutor(webDriver, javaScriptLink);

        List<WebElement> pdfElements = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
            parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber);

        if (!pdfElements.isEmpty()) {
            for (int i = 0; i < pdfElements.size(); i++) {
                String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl, pdfType,
                    pdfElements, childPdfTagType, i);

                if (crawlingDataRepository.existsByPdfUrl(pdfLink)) {
                    continue;
                }

                CrawlingData crawlingData = new CrawlingData(target, pdfLink, titleText);
                crawlingData.setCrawlingTime();

                crawlingDataRepository.save(crawlingData);
            }
        }
    }

    public void goToJavaScriptLink(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String javaScriptLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, int titleOrdinalNumber) {

        javascriptLinkExecutor(webDriver, javaScriptLink);

        List<WebElement> pdfElements = elementFinderUtil.getPdfElements(webDriver, extendedPdfType,
            parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber);
        List<WebElement> titleElements = elementFinderUtil.getTitleElements(webDriver,
            parentTitleIdentifier, parentTitleTagType, childTitleIdentifier, childTitleTagType,
            titleOrdinalNumber);

        if (!pdfElements.isEmpty()) {
            for (int i = 0; i < pdfElements.size(); i++) {
                String pdfLink = checkOnClickPdfUtil.checkOnClickPdf(webDriver, pageUrl, pdfType,
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
    }

    private void javascriptLinkExecutor(WebDriver webDriver, String javaScriptLink) {
        try {
            if (javaScriptLink != null && javaScriptLink.startsWith("javascript:")) {
                String script = javaScriptLink.substring("javascript:".length());
                ((JavascriptExecutor) webDriver).executeScript(script);
            } else {
                ((JavascriptExecutor) webDriver).executeScript(
                    "window.location.href=\"" + javaScriptLink + "\"");
            }
        } catch (Exception e) {
            System.out.println("Skipping href: " + javaScriptLink + " - " + e.getMessage());
        }
    }
}
