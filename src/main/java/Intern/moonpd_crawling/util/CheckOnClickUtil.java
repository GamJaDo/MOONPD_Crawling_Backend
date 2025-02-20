package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.YearType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.util.lstCrawling.HasOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.NoOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.PseudoLinkLstUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.HasOnClickNextPageUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.NoOnClickNextPageUtil;
import Intern.moonpd_crawling.util.yearCrawling.HasOnClickYearUtil;
import Intern.moonpd_crawling.util.yearCrawling.NoOnClickYearUtil;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class CheckOnClickUtil {

    private final HasOnClickLstUtil hasOnClickLstUtil;
    private final NoOnClickLstUtil noOnClickLstUtil;
    private final PseudoLinkLstUtil pseudoLinkLstUtil;
    private final HasOnClickYearUtil hasOnClickYearUtil;
    private final NoOnClickYearUtil noOnClickYearUtil;
    private final HasOnClickNextPageUtil hasOnClickNextPageUtil;
    private final NoOnClickNextPageUtil noOnClickNextPageUtil;
    private final ElementFinderUtil elementFinderUtil;

    public CheckOnClickUtil(HasOnClickLstUtil hasOnClickLstUtil, NoOnClickLstUtil noOnClickLstUtil,
        PseudoLinkLstUtil pseudoLinkLstUtil, HasOnClickYearUtil hasOnClickYearUtil,
        NoOnClickYearUtil noOnClickYearUtil, HasOnClickNextPageUtil hasOnClickNextPageUtil,
        NoOnClickNextPageUtil noOnClickNextPageUtil, ElementFinderUtil elementFinderUtil) {
        this.hasOnClickLstUtil = hasOnClickLstUtil;
        this.noOnClickLstUtil = noOnClickLstUtil;
        this.pseudoLinkLstUtil = pseudoLinkLstUtil;
        this.hasOnClickYearUtil = hasOnClickYearUtil;
        this.noOnClickYearUtil = noOnClickYearUtil;
        this.hasOnClickNextPageUtil = hasOnClickNextPageUtil;
        this.noOnClickNextPageUtil = noOnClickNextPageUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void checkOnClickLst(String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        List<WebElement> lstLinks, LstType lstType, ChildLstTagType childLstTagType,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, int pdfOrdinalNumber,
        String titleText, int index) {

        if (lstType.equals(LstType.HAS_ONCLICK)) {

            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            hasOnClickLstUtil.goToLstByOnclick(pageUrl, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                extendedPdfOrdinalNumber, target, onClickLstScript, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, titleText);
        } else if (lstType.equals(LstType.NO_ONCLICK)) {

            String lstLink = lstLinks.get(index).getAttribute("href");

            noOnClickLstUtil.goToLstByElement(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                childPdfTagType, pdfOrdinalNumber, titleText);
        } else if (lstType.equals(LstType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = lstLinks.get(index);

            pseudoLinkLstUtil.goToPseudoLink(pageUrl, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                extendedPdfOrdinalNumber, target, pseudoLinkElement, pdfType,
                parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                pdfOrdinalNumber, titleText);
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

            System.out.println("###############################");
            System.out.println(yearLink);
            System.out.println("###############################");

            noOnClickYearUtil.goToYearByElement(webDriver, yearLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public void checkOnClickNextPage(WebDriver webDriver, NextPageType nextPageType,
        String nextPageLink) throws InterruptedException {

        if (nextPageType.equals(NextPageType.HAS_ONCLICK)) {
            /*
            String onClickNextPageScript = nextPageLinks.get(currentPage).getAttribute("onclick");

            hasOnClickNextPageUtil.goToNextPageByOnclick(webDriver, onClickNextPageScript);
            Thread.sleep(500);
             */
        } else if (nextPageType.equals(NextPageType.NO_ONCLICK)) {

            noOnClickNextPageUtil.goToNextPageByElement(webDriver, nextPageLink);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }
}
