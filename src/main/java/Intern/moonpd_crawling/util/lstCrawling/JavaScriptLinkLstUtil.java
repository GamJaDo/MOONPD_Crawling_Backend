package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public JavaScriptLinkLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToJavaScriptLink(WebDriver webDriver, String pageUrl, Target target, LinkType lstType,
        ExtendedType extendedPdfType, String extendedPdfIdentifier, TagType extendedPdfTagType,
        SelectorType extendedPdfSelectorType, String javaScriptLink, LinkType pdfType,
        String parentPdfIdentifier, TagType parentPdfTagType, SelectorType parentPdfSelectorType,
        String childPdfIdentifier, TagType childPdfTagType, SelectorType childPdfSelectorType,
        int pdfOrdinalNumber, String parentTitleIdentifier, TagType parentTitleTagType,
        SelectorType parentTitleSelectorType, String childTitleIdentifier, TagType childTitleTagType,
        SelectorType childTitleSelectorType, int titleOrdinalNumber) {

        String lstLink = javascriptLinkExecutor(webDriver, javaScriptLink);

        lstCrawlingService.crawlLst(pageUrl, target, lstType, extendedPdfType,
            extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
            childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier,
            parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber);

    }

    private String javascriptLinkExecutor(WebDriver webDriver, String javaScriptLink) {

        String script = javaScriptLink.substring("javascript:".length());
        ((JavascriptExecutor) webDriver).executeScript(script);

        String lstLink = webDriver.getCurrentUrl();
        webDriver.navigate().back();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return lstLink;
    }
}
