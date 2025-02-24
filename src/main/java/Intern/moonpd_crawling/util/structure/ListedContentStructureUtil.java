package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.selector.child.ChildLstSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.tag.child.ChildYearTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentYearTagType;
import Intern.moonpd_crawling.status.type.ExtendedLstType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.type.YearType;
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
        int totalPage, Target target, ExtendedLstType extendedLstType,
        ParentExtendedLstTagType parentExtendedLstTagType, String parentExtendedLstIdentifier,
        LstType lstType, String parentLstIdentifier, ParentLstTagType parentLstTagType,
        ParentLstSelectorType parentLstSelectorType, String childLstIdentifier,
        ChildLstTagType childLstTagType, ChildLstSelectorType childLstSelectorType,
        int lstOrdinalNumber, ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType,
        int pdfOrdinalNumber, TitleType titleType, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, ParentTitleSelectorType parentTitleSelectorType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType,
        ChildTitleSelectorType childTitleSelectorType, int titleOrdinalNumber,
        NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType,
        ParentNextPageSelectorType parentNextPageSelectorType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType,
        ChildNextPageSelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {

        List<WebElement> lstElements = null;
        List<WebElement> titleElements = null;
        String titleText = null;

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
                    parentExtendedLstTagType, parentExtendedLstIdentifier, lstType,
                    parentLstIdentifier, parentLstTagType, parentLstSelectorType,
                    childLstIdentifier, childLstTagType, childLstSelectorType, lstOrdinalNumber);
                if (lstElements.isEmpty()) {
                    throw new WebDriverException(
                        "No LstElements found for identifier: " + parentLstIdentifier + " or "
                            + childLstIdentifier);
                }

                if (titleType.equals(TitleType.OUT)) {
                    titleElements = elementFinderUtil.getTitleElements(webDriver,
                        parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                        titleOrdinalNumber);
                    if (titleElements.isEmpty()) {
                        throw new WebDriverException(
                            "No TitleElements found for identifier: " + parentTitleIdentifier
                                + " or "
                                + childTitleIdentifier);
                    }
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
                if (titleType.equals(TitleType.OUT)) {
                    if (lstElements.size() != titleElements.size()) {
                        int diff = Math.abs(lstElements.size() - titleElements.size());

                        if (lstElements.size() > titleElements.size()) {
                            lstElements = lstElements.subList(diff, lstElements.size());
                        } else {
                            titleElements = titleElements.subList(diff, titleElements.size());
                        }
                    }
                }

                for (int i = 0; i < lstElements.size(); i++) {
                    Thread.sleep(100);

                    if (titleType.equals(TitleType.OUT)) {
                        titleText = titleElements.get(i).getText();
                    }

                    checkOnClickUtil.checkOnClickLst(webDriver, pageUrl, target,
                        extendedPdfType, parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                        extendedPdfOrdinalNumber, lstElements, lstType, pdfType,
                        parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                        childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                        titleType, parentTitleIdentifier, parentTitleTagType,
                        parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                        childTitleSelectorType, titleOrdinalNumber, titleText, i);
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
