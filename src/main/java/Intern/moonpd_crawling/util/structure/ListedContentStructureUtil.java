package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.selector.child.ChildLstSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedLstSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedLstTagType;
import Intern.moonpd_crawling.status.type.ExtendedLstType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import Intern.moonpd_crawling.util.ElementCountUtil;
import java.util.List;
import java.util.Map;
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

    public void crawl(WebDriver webDriver, StructureType structureType, String pageUrl,
        int totalPage, Target target, ExtendedLstType extendedLstType, String extendedLstIdentifier,
        ExtendedLstTagType extendedLstTagType, ExtendedLstSelectorType extendedLstSelectorType,
        LstType lstType, String parentLstIdentifier, ParentLstTagType parentLstTagType,
        ParentLstSelectorType parentLstSelectorType, String childLstIdentifier,
        ChildLstTagType childLstTagType, ChildLstSelectorType childLstSelectorType,
        int lstOrdinalNumber, ExtendedPdfType extendedPdfType, String extendedPdfIdentifier,
        ExtendedPdfTagType extendedPdfTagType, ExtendedPdfSelectorType extendedPdfSelectorType,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber, NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, ParentNextPageSelectorType parentNextPageSelectorType,
        String childNextPageIdentifier, ChildNextPageTagType childNextPageTagType,
        ChildNextPageSelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {

        List<WebElement> lstElements = null;

        List<WebElement> nextPageElements = elementFinderUtil.getNextPageElements(webDriver,
            nextPageType, parentNextPageIdentifier, parentNextPageTagType,
            parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
            childNextPageSelectorType, nextPageOrdinalNumber);

        List<Map<String, Integer>> nextPageLinks = checkOnClickUtil.getNextPageLinks(totalPage,
            nextPageType, nextPageElements);

        try {
            if (structureType.equals(StructureType.YEAR_FILTERED)) {
                totalPage = elementCountUtil.getTotalPageCnt(nextPageLinks);
            }

            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                lstElements = elementFinderUtil.getLstElements(webDriver, extendedLstType,
                    extendedLstIdentifier, extendedLstTagType, extendedLstSelectorType, lstType,
                    parentLstIdentifier, parentLstTagType, parentLstSelectorType,
                    childLstIdentifier, childLstTagType, childLstSelectorType, lstOrdinalNumber);
                if (lstElements.isEmpty()) {
                    throw new WebDriverException(
                        "No LstElements found for identifier: " + parentLstIdentifier + " or "
                            + childLstIdentifier);
                }

/*
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
                for (int i=0; i<lstElements.size(); i++){
                    System.out.println("lstElements[" + i + "]: " + lstElements.get(i).getAttribute("onclick"));
                }
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

                System.out.println("------------------------------");

                System.out.println("#########################");
                for (int i=0; i<titleElements.size(); i++){
                    System.out.println("titleElements[" + i + "]: " + titleElements.get(i).getText());
                }
                System.out.println("#########################");
*/
                for (int i = 0; i < lstElements.size(); i++) {
                    checkOnClickUtil.checkOnClickLst(webDriver, pageUrl, target, lstType, lstElements,
                        extendedPdfType, extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                        pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                        childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                        parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType, titleOrdinalNumber,
                        i);
                }

                if (currentPage < totalPage) {

                    String nextPageLink = nextPageLinks.get(currentPage).entrySet().iterator().next()
                        .getKey();

                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType, nextPageLink);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
