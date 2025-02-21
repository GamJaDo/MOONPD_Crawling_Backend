package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import Intern.moonpd_crawling.util.ElementCountUtil;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ListedContentStructureUtil {

    private final CheckOnClickUtil checkOnClickUtil;
    private final ElementFinderUtil elementFinderUtil;
    private final ElementCountUtil elementCountUtil;

    public ListedContentStructureUtil(CheckOnClickUtil checkOnClickUtil,
        ElementFinderUtil elementFinderUtil, ElementCountUtil elementCountUtil) {
        this.checkOnClickUtil = checkOnClickUtil;
        this.elementFinderUtil = elementFinderUtil;
        this.elementCountUtil = elementCountUtil;
    }

    public void crawl(WebDriver webDriver, String pageUrl, Target target, LstType lstType,
        String parentLstIdentifier, ParentLstTagType parentLstTagType,
        String childLstIdentifier, ChildLstTagType childLstTagType, int lstOrdinalNumber,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, int pdfOrdinalNumber,
        TitleType titleType, String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType, int nextPageOrdinalNumber) {

        List<WebElement> lstElements = null;
        List<WebElement> titleElements = null;

        List<WebElement> nextPageElements = elementFinderUtil.getNextPageElements(webDriver,
            nextPageType, parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
            childNextPageTagType, nextPageOrdinalNumber);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
        for (int i = 0; i < nextPageElements.size(); i++) {
            System.out.println("nextPageElements[" + i + "]: " + nextPageElements.get(i).getText());
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");

        System.out.println("-------------------------------------------------");

        List<String> nextPageLinks = checkOnClickUtil.getNextPageLink(nextPageType,
            nextPageElements);
        System.out.println("##########################");
        for (int i = 0; i < nextPageLinks.size(); i++) {
            System.out.println("nextPageLinks[" + i + "]: " + nextPageLinks.get(i));
        }
        System.out.println("##########################");

        try {
            int totalPage = elementCountUtil.getTotalPageCnt(nextPageLinks);

            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                lstElements = elementFinderUtil.getLstElements(webDriver,
                    parentLstIdentifier, parentLstTagType, childLstIdentifier, childLstTagType,
                    lstOrdinalNumber);
                if (lstElements.isEmpty()) {
                    throw new WebDriverException(
                        "No LstElements found for identifier: " + parentLstIdentifier + " or "
                            + childLstIdentifier);
                }

                if (titleType.equals(TitleType.OUT)) {
                    titleElements = elementFinderUtil.getTitleElements(webDriver,
                        parentTitleIdentifier,
                        parentTitleTagType, childTitleIdentifier, childTitleTagType,
                        titleOrdinalNumber);
                    if (titleElements.isEmpty()) {
                        throw new WebDriverException(
                            "No TitleElements found for identifier: " + parentTitleIdentifier
                                + " or "
                                + childTitleIdentifier);
                    }

                    System.out.println("lstElements.size(): " + lstElements.size());
                    System.out.println("titleElements.size(): " + titleElements.size());
                }

                /*
                if (lstElements.size() != titleElements.size()) {
                    int diff = Math.abs(lstElements.size() - titleElements.size());

                    if (lstElements.size() > titleElements.size()) {
                        lstElements = lstElements.subList(diff, lstElements.size());
                    } else {
                        titleElements = titleElements.subList(diff, titleElements.size());
                    }
                }
                 */

                for (int i = 0; i < lstElements.size(); i++) {
                    Thread.sleep(100);

                    if (titleType.equals(TitleType.OUT)) {
                        String titleText = titleElements.get(i).getText();

                        checkOnClickUtil.checkOnClickLstWithTitle(webDriver, pageUrl, target,
                            extendedPdfType, parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                            extendedPdfOrdinalNumber, lstElements, lstType, pdfType,
                            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                            childPdfTagType, pdfOrdinalNumber, titleText, i);
                    } else if (titleType.equals(TitleType.IN)) {
                        checkOnClickUtil.checkOnClickLst(webDriver, pageUrl, target,
                            extendedPdfType, parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                            extendedPdfOrdinalNumber, lstElements, lstType, pdfType,
                            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                            childPdfTagType, pdfOrdinalNumber, titleType, parentTitleIdentifier,
                            parentTitleTagType, childTitleIdentifier, childTitleTagType,
                            titleOrdinalNumber, i);
                    } else {
                        throw new WebDriverException("Unsupported title type");
                    }
                }

                if (currentPage < totalPage) {
                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType,
                        nextPageLinks.get(currentPage));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
