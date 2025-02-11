package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.BackType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.child.ChildBackTagType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentBackTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.BackCrawling.HasOnClickBackUtil;
import Intern.moonpd_crawling.util.BackCrawling.NoOnClickBackUtil;
import Intern.moonpd_crawling.util.lstCrawling.HasOnClickLstUtil;
import Intern.moonpd_crawling.util.lstCrawling.NoOnClickLstUtil;
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
    private final HasOnClickBackUtil hasOnClickBackUtil;
    private final NoOnClickBackUtil noOnClickBackUtil;
    private final HasOnClickNextPageUtil hasOnClickNextPageUtil;
    private final NoOnClickNextPageUtil noOnClickNextPageUtil;
    private final ElementFinderUtil elementFinderUtil;

    public CheckOnClickUtil(HasOnClickLstUtil hasOnClickLstUtil, NoOnClickLstUtil noOnClickLstUtil,
        HasOnClickBackUtil hasOnClickBackUtil, NoOnClickBackUtil noOnClickBackUtil,
        HasOnClickNextPageUtil hasOnClickNextPageUtil, NoOnClickNextPageUtil noOnClickNextPageUtil,
        ElementFinderUtil elementFinderUtil) {
        this.hasOnClickLstUtil = hasOnClickLstUtil;
        this.noOnClickLstUtil = noOnClickLstUtil;
        this.hasOnClickBackUtil = hasOnClickBackUtil;
        this.noOnClickBackUtil = noOnClickBackUtil;
        this.hasOnClickNextPageUtil = hasOnClickNextPageUtil;
        this.noOnClickNextPageUtil = noOnClickNextPageUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    private void checkOnClickBack(WebDriver webDriver, BackType backType,
        String parentBackIdentifier, ParentBackTagType parentBackTagType,
        String childBackIdentifier, ChildBackTagType childBackTagType, int backOrdinalNumber) {

        if (backType.equals(BackType.HAS_ONCLICK)) {
            WebElement backElement = elementFinderUtil.getBackElement(
                webDriver, parentBackIdentifier, parentBackTagType,
                childBackIdentifier, childBackTagType, backOrdinalNumber);

            String onClickBackScript = backElement.getAttribute("onclick");

            hasOnClickBackUtil.goToBackByOnclick(webDriver, onClickBackScript);
        } else if (backType.equals(BackType.NO_ONCLICK)) {
            WebElement backElement = elementFinderUtil.getBackElement(webDriver,
                parentBackIdentifier,
                parentBackTagType, childBackIdentifier, childBackTagType, backOrdinalNumber);

            noOnClickBackUtil.goToBackByElement(backElement);
        } else {
            throw new WebDriverException("Unsupported back type");
        }
    }

    public void checkOnClickLst(WebDriver webDriver, Target target, List<WebElement> lstLinks,
        LstType lstType, ChildLstTagType childLstTagType,
        BackType backType, String parentBackIdentifier, ParentBackTagType parentBackTagType,
        String childBackIdentifier, ChildBackTagType childBackTagType, int backOrdinalNumber,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        int pdfOrdinalNumber, String titleText, int index) {

        List<WebElement> pdfLinks = null;
        List<WebElement> titles = null;

        if (lstType.equals(LstType.HAS_ONCLICK)) {
            String onClickLstScript = lstLinks.get(index).getAttribute("onclick");

            pdfLinks = elementFinderUtil.getPdfElements(webDriver,
                parentPdfIdentifier,
                parentPdfTagType,
                childPdfIdentifier, childPdfTagType, pdfOrdinalNumber);

            hasOnClickLstUtil.goToLstByOnclick(webDriver, target, onClickLstScript, pdfLinks,
                titleText, childPdfTagType);

            checkOnClickBack(webDriver, backType, parentBackIdentifier, parentBackTagType,
                childBackIdentifier, childBackTagType, backOrdinalNumber);
        } else if (lstType.equals(LstType.NO_ONCLICK)) {
            String lstLink = elementFinderUtil.getLstLink(lstLinks, childLstTagType, index);

            noOnClickLstUtil.goToLstByElement(target, lstLink, parentPdfIdentifier,
                parentPdfTagType,
                childPdfIdentifier, childPdfTagType, pdfOrdinalNumber, titleText);
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
