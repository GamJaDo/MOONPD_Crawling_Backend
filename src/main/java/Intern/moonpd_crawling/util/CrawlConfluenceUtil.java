package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.util.lstCrawling.HrefLinkLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.JavaScriptLinkLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.OnClickLinkLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.PseudoLinkLstUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.HrefLinkNextPageUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.OnClickLinkNextPageUtil;
import Intern.moonpd_crawling.util.yearCrawling.HrefLinkYearUtil;
import Intern.moonpd_crawling.util.yearCrawling.OnClickLinkYearUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class CrawlConfluenceUtil {

    private final OnClickLinkLstUtil onClickLinkLstUtil;
    private final HrefLinkLstUtil hrefLinkLstUtil;
    private final PseudoLinkLstUtil pseudoLinkLstUtil;
    private final JavaScriptLinkLstUtil javaScriptLinkLstUtil;
    private final OnClickLinkYearUtil onClickLinkYearUtil;
    private final HrefLinkYearUtil hrefLinkYearUtil;
    private final OnClickLinkNextPageUtil onClickLinkNextPageUtil;
    private final HrefLinkNextPageUtil hrefLinkNextPageUtil;

    public CrawlConfluenceUtil(OnClickLinkLstUtil onClickLinkLstUtil, HrefLinkLstUtil hrefLinkLstUtil,
        PseudoLinkLstUtil pseudoLinkLstUtil, JavaScriptLinkLstUtil javaScriptLinkLstUtil,
        OnClickLinkYearUtil onClickLinkYearUtil, HrefLinkYearUtil hrefLinkYearUtil,
        OnClickLinkNextPageUtil onClickLinkNextPageUtil, HrefLinkNextPageUtil hrefLinkNextPageUtil) {
        this.onClickLinkLstUtil = onClickLinkLstUtil;
        this.hrefLinkLstUtil = hrefLinkLstUtil;
        this.pseudoLinkLstUtil = pseudoLinkLstUtil;
        this.javaScriptLinkLstUtil = javaScriptLinkLstUtil;
        this.onClickLinkYearUtil = onClickLinkYearUtil;
        this.hrefLinkYearUtil = hrefLinkYearUtil;
        this.onClickLinkNextPageUtil = onClickLinkNextPageUtil;
        this.hrefLinkNextPageUtil = hrefLinkNextPageUtil;
    }

    public void crawlLst(WebDriver webDriver, String pageUrl, Target target, LinkType lstType,
        List<WebElement> lstLinks, ExtendedType extendedPdfType, String extendedPdfIdentifier,
        TagType extendedPdfTagType, SelectorType extendedPdfSelectorType, LinkType pdfType,
        String parentPdfIdentifier, TagType parentPdfTagType, SelectorType parentPdfSelectorType,
        String childPdfIdentifier, TagType childPdfTagType, SelectorType childPdfSelectorType,
        int pdfOrdinalNumber, TitleType titleType, String parentTitleIdentifier, TagType parentTitleTagType,
        SelectorType parentTitleSelectorType, String childTitleIdentifier, TagType childTitleTagType,
        SelectorType childTitleSelectorType, int titleOrdinalNumber, String titleText, int index) {

        if (lstType.equals(LinkType.ONCLICK_LINK)) {

            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            onClickLinkLstUtil.goToLstByOnclick(pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                onClickLstScript, pdfType, parentPdfIdentifier, parentPdfTagType,
                parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                titleOrdinalNumber, titleText);
        } else if (lstType.equals(LinkType.HREF_LINK)) {

            String lstLink = lstLinks.get(index).getAttribute("href");

            hrefLinkLstUtil.goToLstByHref(pageUrl, target, lstType, extendedPdfType, extendedPdfIdentifier,
                extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType, parentPdfIdentifier,
                parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                childPdfSelectorType, pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                titleOrdinalNumber, titleText);
        } else if (lstType.equals(LinkType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = lstLinks.get(index);

            pseudoLinkLstUtil.goToPseudoLink(pageUrl, target, lstType, extendedPdfType, extendedPdfIdentifier,
                extendedPdfTagType, extendedPdfSelectorType, pseudoLinkElement, pdfType, parentPdfIdentifier,
                parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                childPdfSelectorType, pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                titleOrdinalNumber, titleText);
        } else if (lstType.equals(LinkType.JAVASCRIPT_LINK)) {

            String javaScriptLink = lstLinks.get(index).getAttribute("href");

            javaScriptLinkLstUtil.goToJavaScriptLink(webDriver, pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, javaScriptLink, pdfType,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
                childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, titleType, parentTitleIdentifier,
                parentTitleTagType, parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                childTitleSelectorType, titleOrdinalNumber, titleText);
        } else {
            throw new WebDriverException("Unsupported lst type");
        }
    }

    public void crawlYear(WebDriver webDriver, LinkType yearType, String yearLink)
        throws InterruptedException {

        if (yearType.equals(LinkType.ONCLICK_LINK)) {

            onClickLinkYearUtil.goToYearByOnclick(webDriver, yearLink);
            Thread.sleep(500);
        } else if (yearType.equals(LinkType.HREF_LINK) || yearType.equals(LinkType.OPTION_LINK)) {

            hrefLinkYearUtil.goToYearByHref(webDriver, yearLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public void crawlNextPage(WebDriver webDriver, LinkType nextPageType,
        String nextPageLink) throws InterruptedException {

        if (nextPageType.equals(LinkType.ONCLICK_LINK) || nextPageType.equals(LinkType.JAVASCRIPT_LINK)) {

            onClickLinkNextPageUtil.goToNextPageByOnclick(webDriver, nextPageLink);
            Thread.sleep(500);
        } else if (nextPageType.equals(LinkType.HREF_LINK)) {

            hrefLinkNextPageUtil.goToNextPageByHref(webDriver, nextPageLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }
}
