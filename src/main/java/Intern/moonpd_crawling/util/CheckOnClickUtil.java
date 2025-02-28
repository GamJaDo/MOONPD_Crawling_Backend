package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
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

    public void checkOnClickLst(WebDriver webDriver, String pageUrl, Target target, LinkType lstType,
        List<WebElement> lstLinks, ExtendedType extendedPdfType, String extendedPdfIdentifier,
        TagType extendedPdfTagType, SelectorType extendedPdfSelectorType, LinkType pdfType,
        String parentPdfIdentifier, TagType parentPdfTagType, SelectorType parentPdfSelectorType,
        String childPdfIdentifier, TagType childPdfTagType, SelectorType childPdfSelectorType,
        int pdfOrdinalNumber, String parentTitleIdentifier, TagType parentTitleTagType,
        SelectorType parentTitleSelectorType, String childTitleIdentifier, TagType childTitleTagType,
        SelectorType childTitleSelectorType, int titleOrdinalNumber, int index) {

        if (lstType.equals(LinkType.ONCLICK_LINK)) {

            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            hasOnClickLstUtil.goToLstByOnclick(pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                onClickLstScript, pdfType, parentPdfIdentifier, parentPdfTagType,
                parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                childTitleIdentifier, childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
        } else if (lstType.equals(LinkType.HREF_LINK)) {

            String lstLink = lstLinks.get(index).getAttribute("href");

            noOnClickLstUtil.goToLstByHref(pageUrl, target, lstType, extendedPdfType, extendedPdfIdentifier,
                extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType, parentPdfIdentifier,
                parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                titleOrdinalNumber);
        } else if (lstType.equals(LinkType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = lstLinks.get(index);

            pseudoLinkLstUtil.goToPseudoLink(pageUrl, target, lstType, extendedPdfType, extendedPdfIdentifier,
                extendedPdfTagType, extendedPdfSelectorType, pseudoLinkElement, pdfType, parentPdfIdentifier,
                parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                parentTitleSelectorType, childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                titleOrdinalNumber);
        } else if (lstType.equals(LinkType.JAVASCRIPT_LINK)) {

            String javaScriptLink = lstLinks.get(index).getAttribute("href");

            javaScriptLinkLstUtil.goToJavaScriptLink(webDriver, pageUrl, target, lstType, extendedPdfType,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, javaScriptLink, pdfType,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
                childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier,
                parentTitleTagType, parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                childTitleSelectorType, titleOrdinalNumber);
        } else {
            throw new WebDriverException("Unsupported lst type");
        }
    }

    public List<Map<String, Integer>> getYearLinks(String pageUrl, LinkType yearType,
        List<WebElement> yearElements) {

        List<Map<String, Integer>> yearPageLinks = new ArrayList<>();

        if (yearType.equals(LinkType.OPTION_LINK)) {
            int startYear = Integer.parseInt(yearElements.get(0).getText().replaceAll("[^0-9]", ""));
            Map<String, Integer> map = new HashMap<>();

            for (int i = 0; i < yearElements.size(); i++) {
                map.put(pageUrl.replaceAll(String.valueOf(startYear), String.valueOf(startYear - i)),
                    startYear - i);
                yearPageLinks.add(map);
            }

            return yearPageLinks;
        } else if (yearType.equals(LinkType.ONCLICK_LINK)) {

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
        } else if (yearType.equals(LinkType.HREF_LINK) || yearType.equals(LinkType.JAVASCRIPT_LINK)) {

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

    public void checkOnClickYear(WebDriver webDriver, LinkType yearType, String yearLink)
        throws InterruptedException {

        if (yearType.equals(LinkType.ONCLICK_LINK)) {

            hasOnClickYearUtil.goToYearByOnclick(webDriver, yearLink);
            Thread.sleep(500);
        } else if (yearType.equals(LinkType.HREF_LINK)) {

            noOnClickYearUtil.goToYearByHref(webDriver, yearLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public List<Map<String, Integer>> getNextPageLinks(int totalPage, LinkType nextPageType,
        List<WebElement> nextPageElements) {

        List<Map<String, Integer>> nextPageLinks = new ArrayList<>();

        if (nextPageType.equals(LinkType.ONCLICK_LINK)) {

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
        } else if (nextPageType.equals(LinkType.HREF_LINK) || nextPageType.equals(LinkType.JAVASCRIPT_LINK)) {

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
        } else if (nextPageType.equals(LinkType.NONE)) {

            return nextPageLinks;
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }

    public void checkOnClickNextPage(WebDriver webDriver, LinkType nextPageType,
        String nextPageLink) throws InterruptedException {

        if (nextPageType.equals(LinkType.ONCLICK_LINK) || nextPageType.equals(LinkType.JAVASCRIPT_LINK)) {

            hasOnClickNextPageUtil.goToNextPageByOnclick(webDriver, nextPageLink);
            Thread.sleep(500);
        } else if (nextPageType.equals(LinkType.HREF_LINK)) {

            noOnClickNextPageUtil.goToNextPageByHref(webDriver, nextPageLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }
}
