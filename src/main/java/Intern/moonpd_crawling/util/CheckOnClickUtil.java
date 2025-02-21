package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.YearType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.lstCrawling.HasOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.JavaScriptLinkLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.NoOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.PseudoLinkLstUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.HasOnClickNextPageUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.NoOnClickNextPageUtil;
import Intern.moonpd_crawling.util.yearCrawling.HasOnClickYearUtil;
import Intern.moonpd_crawling.util.yearCrawling.NoOnClickYearUtil;
import java.util.ArrayList;
import java.util.List;
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

    public CheckOnClickUtil(HasOnClickLstUtil hasOnClickLstUtil, NoOnClickLstUtil noOnClickLstUtil,
        PseudoLinkLstUtil pseudoLinkLstUtil, JavaScriptLinkLstUtil javaScriptLinkLstUtil,
        HasOnClickYearUtil hasOnClickYearUtil, NoOnClickYearUtil noOnClickYearUtil,
        HasOnClickNextPageUtil hasOnClickNextPageUtil,
        NoOnClickNextPageUtil noOnClickNextPageUtil) {
        this.hasOnClickLstUtil = hasOnClickLstUtil;
        this.noOnClickLstUtil = noOnClickLstUtil;
        this.pseudoLinkLstUtil = pseudoLinkLstUtil;
        this.javaScriptLinkLstUtil = javaScriptLinkLstUtil;
        this.hasOnClickYearUtil = hasOnClickYearUtil;
        this.noOnClickYearUtil = noOnClickYearUtil;
        this.hasOnClickNextPageUtil = hasOnClickNextPageUtil;
        this.noOnClickNextPageUtil = noOnClickNextPageUtil;
    }

    public void checkOnClickLstWithTitle(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        List<WebElement> lstLinks, LstType lstType, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String titleText, int index) {

        if (lstType.equals(LstType.HAS_ONCLICK)) {

            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            hasOnClickLstUtil.goToLstByOnclickWithTitle(pageUrl, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                extendedPdfOrdinalNumber, target, onClickLstScript, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, titleText);
        } else if (lstType.equals(LstType.NO_ONCLICK)) {

            String lstLink = lstLinks.get(index).getAttribute("href");

            noOnClickLstUtil.goToLstByHrefWithTitle(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                childPdfTagType, pdfOrdinalNumber, titleText);
        } else if (lstType.equals(LstType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = lstLinks.get(index);

            pseudoLinkLstUtil.goToPseudoLinkWithTitle(pageUrl, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                extendedPdfOrdinalNumber, target, pseudoLinkElement, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, titleText);
        } else if (lstType.equals(LstType.JAVASCRIPT_LINK)) {

            String javaScriptLink = lstLinks.get(index).getAttribute("href");

            javaScriptLinkLstUtil.goToJavaScriptLinkWithTitle(webDriver, pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                javaScriptLink, pdfType, parentPdfIdentifier, parentPdfTagType,
                childPdfIdentifier, childPdfTagType, pdfOrdinalNumber, titleText);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webDriver.navigate().back();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new WebDriverException("Unsupported lst type");
        }
    }

    public void checkOnClickLst(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        List<WebElement> lstLinks, LstType lstType, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        int index) {

        if (lstType.equals(LstType.HAS_ONCLICK)) {

            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            hasOnClickLstUtil.goToLstByOnclick(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                extendedPdfOrdinalNumber, onClickLstScript, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
                childTitleTagType, titleOrdinalNumber);
        } else if (lstType.equals(LstType.NO_ONCLICK)) {

            String lstLink = lstLinks.get(index).getAttribute("href");

            noOnClickLstUtil.goToLstByHref(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                childPdfTagType, pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                childTitleIdentifier, childTitleTagType, titleOrdinalNumber);
        } else if (lstType.equals(LstType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = lstLinks.get(index);

            pseudoLinkLstUtil.goToPseudoLink(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                extendedPdfOrdinalNumber, pseudoLinkElement, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
                childTitleTagType, titleOrdinalNumber);
        } else if (lstType.equals(LstType.JAVASCRIPT_LINK)) {

            String javaScriptLink = lstLinks.get(index).getAttribute("href");

            javaScriptLinkLstUtil.goToJavaScriptLink(webDriver, pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                javaScriptLink, pdfType, parentPdfIdentifier, parentPdfTagType,
                childPdfIdentifier, childPdfTagType, pdfOrdinalNumber, parentTitleIdentifier,
                parentTitleTagType, childTitleIdentifier, childTitleTagType, titleOrdinalNumber);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webDriver.navigate().back();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new WebDriverException("Unsupported lst type");
        }
    }

    public void checkOnClickYear(WebDriver webDriver, YearType yearType, String yearLink)
        throws InterruptedException {

        if (yearType.equals(YearType.HAS_ONCLICK)) {

            hasOnClickYearUtil.goToYearByOnclick();
            Thread.sleep(500);
        } else if (yearType.equals(YearType.NO_ONCLICK)) {

            noOnClickYearUtil.goToYearByHref(webDriver, yearLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public List<String> checkOnClickNextPageLink(NextPageType nextPageType,
        List<WebElement> nextPageElements) {

        if (nextPageType.equals(NextPageType.HAS_ONCLICK)) {

            List<String> nextPageLinksByOnClick = new ArrayList<>();
            nextPageLinksByOnClick.add("1");

            for (WebElement nextPageElement : nextPageElements) {
                String text = nextPageElement.getText().trim();
                if (!text.isEmpty() && text.chars().allMatch(Character::isDigit)) {
                    nextPageLinksByOnClick.add(nextPageElement.getAttribute("onclick"));
                }
            }

            return nextPageLinksByOnClick;
        } else if (nextPageType.equals(NextPageType.NO_ONCLICK) || nextPageType.equals(
            NextPageType.JAVASCRIPT_LINK)) {

            List<String> nextPageLinksByHref = new ArrayList<>();
            nextPageLinksByHref.add("1");

            for (WebElement nextPageElement : nextPageElements) {
                String text = nextPageElement.getText().trim();
                if (!text.isEmpty() && text.chars().allMatch(Character::isDigit)) {
                    nextPageLinksByHref.add(nextPageElement.getAttribute("href"));
                }
            }

            return nextPageLinksByHref;
        } else if (nextPageType.equals(NextPageType.NONE)) {
            return new ArrayList<>();
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
