package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.CrawlingData;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.service.LstCrawlingService;
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
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;
    private final CrawlingDataRepository crawlingDataRepository;
    private final CheckOnClickPdfUtil checkOnClickPdfUtil;
    private final ElementFinderUtil elementFinderUtil;

    public JavaScriptLinkLstUtil(LstCrawlingService lstCrawlingService,
        CrawlingDataRepository crawlingDataRepository,
        CheckOnClickPdfUtil checkOnClickPdfUtil, ElementFinderUtil elementFinderUtil) {
        this.lstCrawlingService = lstCrawlingService;
        this.crawlingDataRepository = crawlingDataRepository;
        this.checkOnClickPdfUtil = checkOnClickPdfUtil;
        this.elementFinderUtil = elementFinderUtil;
    }
/*
    public void goToJavaScriptLinkWithTitle(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String javaScriptLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String titleText) {

        String lstLink = javascriptLinkExecutor(webDriver, javaScriptLink);

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
    */

    public void goToJavaScriptLink(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String javaScriptLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, int titleOrdinalNumber) {

        String lstLink = javascriptLinkExecutor(webDriver, javaScriptLink);

        lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType, parentExtendedPdfIdentifier,
            parentExtendedPdfTagType, extendedPdfOrdinalNumber, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
            childTitleTagType, titleOrdinalNumber);
/*
        System.out.println("@@@@@@@@@@@@@@@@@@");
        System.out.println("lstLink: " + lstLink);
        System.out.println("@@@@@@@@@@@@@@@@@@");

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
 */
    }

    private String javascriptLinkExecutor(WebDriver webDriver, String javaScriptLink) {

        String lstLink;

        if (javaScriptLink != null && javaScriptLink.startsWith("javascript:")) {
            String script = javaScriptLink.substring("javascript:".length());
            ((JavascriptExecutor) webDriver).executeScript(script);
        } else {
            ((JavascriptExecutor) webDriver).executeScript("window.location.href='" + javaScriptLink + "'");
        }

        lstLink = webDriver.getCurrentUrl();
        webDriver.navigate().back();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return lstLink;
    }
}
