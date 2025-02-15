package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.util.lstCrawling.HasOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.NoOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.PseudoLinkLstUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.HasOnClickNextPageUtil;
import Intern.moonpd_crawling.util.nextPageCrawling.NoOnClickNextPageUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class CheckOnClickUtil {

    private final HasOnClickLstUtil hasOnClickLstUtil;
    private final NoOnClickLstUtil noOnClickLstUtil;
    private final PseudoLinkLstUtil pseudoLinkLstUtil;
    private final HasOnClickNextPageUtil hasOnClickNextPageUtil;
    private final NoOnClickNextPageUtil noOnClickNextPageUtil;
    private final ElementFinderUtil elementFinderUtil;

    public CheckOnClickUtil(HasOnClickLstUtil hasOnClickLstUtil, NoOnClickLstUtil noOnClickLstUtil,
        PseudoLinkLstUtil pseudoLinkLstUtil,
        HasOnClickNextPageUtil hasOnClickNextPageUtil, NoOnClickNextPageUtil noOnClickNextPageUtil,
        ElementFinderUtil elementFinderUtil) {
        this.hasOnClickLstUtil = hasOnClickLstUtil;
        this.noOnClickLstUtil = noOnClickLstUtil;
        this.pseudoLinkLstUtil = pseudoLinkLstUtil;
        this.hasOnClickNextPageUtil = hasOnClickNextPageUtil;
        this.noOnClickNextPageUtil = noOnClickNextPageUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void checkOnClickLst(String pageUrl, Target target,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        List<WebElement> lstLinks,
        LstType lstType, ChildLstTagType childLstTagType,
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

            String lstLink = elementFinderUtil.getLstLink(lstLinks, childLstTagType, index);

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

    public void checkOnClickNextPage(WebDriver webDriver, NextPageType nextPageType,
        String nextIdentifier, int currentPage) throws InterruptedException {

        if (nextPageType.equals(NextPageType.HAS_ONCLICK)) {
            hasOnClickNextPageUtil.goToNextPageByOnclick(webDriver, nextIdentifier,
                currentPage + 1);
            Thread.sleep(500);
        } else if (nextPageType.equals(NextPageType.NO_ONCLICK)) {
            noOnClickNextPageUtil.goToNextPageByElement(webDriver, nextIdentifier,
                currentPage + 1);
            Thread.sleep(500);
        } else {
            throw new WebDriverException("No nextPage found for identifier: " + nextIdentifier);
        }
    }
}
