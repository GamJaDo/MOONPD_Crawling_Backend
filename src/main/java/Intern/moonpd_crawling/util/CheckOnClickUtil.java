package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.YearType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.lstCrawling.HasOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.JavaScriptLinkLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.NoOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.PseudoLinkLstUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.HasOnClickNextPageUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.NoOnClickNextPageUtil;
import Intern.moonpd_crawling.util.yearCrawling.HasOnClickYearUtil;
import Intern.moonpd_crawling.util.yearCrawling.NoOnClickYearUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class CheckOnClickUtil {

    private final HasOnClickLstUtil hasOnClickLstUtil;
    private final NoOnClickLstUtil noOnClickLstUtil;
    private final PseudoLinkLstUtil pseudoLinkLstUtil;
    private final JavaScriptLinkLstUtil javaScriptLinkLstUtil;
    private final HasOnClickYearUtil hasOnClickYearUtil;
    private final NoOnClickYearUtil noOnClickYearUtil;
    private final HasOnClickNextPageUtil hasOnClickNextPageUtil;
    private final NoOnClickNextPageUtil noOnClickNextPageUtil;
    private final ElementCountUtil elementCountUtil;
    private final NextPageLinkExtenderUtil nextPageLinkExtenderUtil;

    public CheckOnClickUtil(HasOnClickLstUtil hasOnClickLstUtil, NoOnClickLstUtil noOnClickLstUtil,
        PseudoLinkLstUtil pseudoLinkLstUtil, JavaScriptLinkLstUtil javaScriptLinkLstUtil,
        HasOnClickYearUtil hasOnClickYearUtil, NoOnClickYearUtil noOnClickYearUtil,
        HasOnClickNextPageUtil hasOnClickNextPageUtil, NoOnClickNextPageUtil noOnClickNextPageUtil,
        ElementCountUtil elementCountUtil, NextPageLinkExtenderUtil nextPageLinkExtenderUtil) {
        this.hasOnClickLstUtil = hasOnClickLstUtil;
        this.noOnClickLstUtil = noOnClickLstUtil;
        this.pseudoLinkLstUtil = pseudoLinkLstUtil;
        this.javaScriptLinkLstUtil = javaScriptLinkLstUtil;
        this.hasOnClickYearUtil = hasOnClickYearUtil;
        this.noOnClickYearUtil = noOnClickYearUtil;
        this.hasOnClickNextPageUtil = hasOnClickNextPageUtil;
        this.noOnClickNextPageUtil = noOnClickNextPageUtil;
        this.elementCountUtil = elementCountUtil;
        this.nextPageLinkExtenderUtil = nextPageLinkExtenderUtil;
    }

    public void checkOnClickLst(WebDriver webDriver, String pageUrl, Target target,
        LstType lstType, List<WebElement> lstLinks,
        ExtendedPdfType extendedPdfType, String extendedPdfIdentifier,
        ExtendedPdfTagType extendedPdfTagType, ExtendedPdfSelectorType extendedPdfSelectorType,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber, int index) {

        if (lstType.equals(LstType.HAS_ONCLICK)) {

            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            hasOnClickLstUtil.goToLstByOnclick(pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                onClickLstScript, pdfType, parentPdfIdentifier, parentPdfTagType,
                parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                childTitleSelectorType, titleOrdinalNumber);
        } else if (lstType.equals(LstType.NO_ONCLICK)) {

            String lstLink = lstLinks.get(index).getAttribute("href");

            noOnClickLstUtil.goToLstByHref(pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType,
                parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                childTitleSelectorType, titleOrdinalNumber);
        } else if (lstType.equals(LstType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = lstLinks.get(index);

            pseudoLinkLstUtil.goToPseudoLink(pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                pseudoLinkElement, pdfType, parentPdfIdentifier, parentPdfTagType,
                parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                childTitleSelectorType, titleOrdinalNumber);
        } else if (lstType.equals(LstType.JAVASCRIPT_LINK)) {

            String javaScriptLink = lstLinks.get(index).getAttribute("href");

            javaScriptLinkLstUtil.goToJavaScriptLink(webDriver, pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                javaScriptLink, pdfType, parentPdfIdentifier, parentPdfTagType,
                parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                childTitleSelectorType, titleOrdinalNumber);
        } else {
            throw new WebDriverException("Unsupported lst type");
        }
    }

    public List<Map<String, Integer>> getYearLinks(YearType yearType, List<WebElement> yearElements) {

        List<Map<String, Integer>> yearPageLinks = new ArrayList<>();

        if (yearType.equals(YearType.HAS_ONCLICK)) {

            for (WebElement yearElement : yearElements) {
                String text = yearElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(yearElement.getAttribute("onclick"), pageNumber);
                    yearPageLinks.add(map);
                }
            }

            yearPageLinks = elementCountUtil.checkFirstNextPageLink(yearPageLinks);

            return yearPageLinks;
        } else if (yearType.equals(YearType.NO_ONCLICK) || yearType.equals(YearType.JAVASCRIPT_LINK)) {

            for (WebElement yearElement : yearElements) {
                String text = yearElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(yearElement.getAttribute("href"), pageNumber);
                    yearPageLinks.add(map);
                }
            }

            yearPageLinks = elementCountUtil.checkFirstNextPageLink(yearPageLinks);

            return yearPageLinks;
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public void checkOnClickYear(WebDriver webDriver, YearType yearType, String yearLink)
        throws InterruptedException {

        if (yearType.equals(YearType.HAS_ONCLICK)) {

            hasOnClickYearUtil.goToYearByOnclick(webDriver, yearLink);
            Thread.sleep(500);
        } else if (yearType.equals(YearType.NO_ONCLICK)) {

            noOnClickYearUtil.goToYearByHref(webDriver, yearLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public List<Map<String, Integer>> getNextPageLinks(int totalPage, NextPageType nextPageType,
        List<WebElement> nextPageElements) {

        List<Map<String, Integer>> nextPageLinks = new ArrayList<>();

        if (nextPageType.equals(NextPageType.HAS_ONCLICK)) {

            for (WebElement nextPageElement : nextPageElements) {
                String text = nextPageElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(nextPageElement.getAttribute("onclick"), pageNumber);
                    nextPageLinks.add(map);
                }
            }

            nextPageLinks = elementCountUtil.checkFirstNextPageLink(nextPageLinks);
            nextPageLinks = nextPageLinkExtenderUtil.extendedNextPageLinks(totalPage, nextPageLinks);

            return nextPageLinks;
        } else if (nextPageType.equals(NextPageType.NO_ONCLICK) || nextPageType.equals(
            NextPageType.JAVASCRIPT_LINK)) {

            for (WebElement nextPageElement : nextPageElements) {
                String text = nextPageElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(nextPageElement.getAttribute("href"), pageNumber);
                    nextPageLinks.add(map);
                }
            }

            nextPageLinks = elementCountUtil.checkFirstNextPageLink(nextPageLinks);
            nextPageLinks = nextPageLinkExtenderUtil.extendedNextPageLinks(totalPage, nextPageLinks);

            return nextPageLinks;
        } else if (nextPageType.equals(NextPageType.NONE)) {

            return nextPageLinks;
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }

    public void checkOnClickNextPage(WebDriver webDriver, NextPageType nextPageType,
        String nextPageLink) throws InterruptedException {

        if (nextPageType.equals(NextPageType.HAS_ONCLICK) || nextPageType.equals(
            NextPageType.JAVASCRIPT_LINK)) {

            hasOnClickNextPageUtil.goToNextPageByOnclick(webDriver, nextPageLink);
            Thread.sleep(500);
        } else if (nextPageType.equals(NextPageType.NO_ONCLICK)) {

            noOnClickNextPageUtil.goToNextPageByHref(webDriver, nextPageLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }
}
