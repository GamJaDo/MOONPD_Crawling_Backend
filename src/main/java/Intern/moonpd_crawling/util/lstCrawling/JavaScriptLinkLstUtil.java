package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptLinkLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public JavaScriptLinkLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToJavaScriptLink(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String javaScriptLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, ParentPdfSelectorType parentPdfSelectorType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber, String titleText) {

        String lstLink = javascriptLinkExecutor(webDriver, javaScriptLink);

        if (titleType.equals(TitleType.OUT)) {
            lstCrawlingService.crawlLstWithTitleText(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                titleText);
        } else if (titleType.equals(TitleType.IN)) {
            lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier,
                parentExtendedPdfTagType, extendedPdfOrdinalNumber, lstLink, pdfType,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
                childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier,
                parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
                childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
        }
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
