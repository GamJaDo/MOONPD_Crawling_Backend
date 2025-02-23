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
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.type.YearType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.child.ChildYearTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentYearTagType;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementCountUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class YearFilteredStructureUtil {

    private final SinglePageStructureUtil singlePageStructureUtil;
    private final ListedContentStructureUtil listedContentStructureUtil;
    private final CheckOnClickUtil checkOnClickUtil;
    private final ElementFinderUtil elementFinderUtil;
    private final ElementCountUtil elementCountUtil;

    public YearFilteredStructureUtil(SinglePageStructureUtil singlePageStructureUtil,
        ListedContentStructureUtil listedContentStructureUtil, CheckOnClickUtil checkOnClickUtil,
        ElementFinderUtil elementFinderUtil, ElementCountUtil elementCountUtil) {
        this.singlePageStructureUtil = singlePageStructureUtil;
        this.listedContentStructureUtil = listedContentStructureUtil;
        this.checkOnClickUtil = checkOnClickUtil;
        this.elementFinderUtil = elementFinderUtil;
        this.elementCountUtil = elementCountUtil;
    }

    public void crawl(WebDriver webDriver, StructureType structureType, String pageUrl,
        int totalPage, Target target, LstType lstType, String parentLstIdentifier,
        ParentLstTagType parentLstTagType, ParentLstSelectorType parentLstSelectorType,
        String childLstIdentifier, ChildLstTagType childLstTagType,
        ChildLstSelectorType childLstSelectorType, int lstOrdinalNumber, YearType yearType,
        String parentYearIdentifier, ParentYearTagType parentYearTagType,
        String childYearIdentifier, ChildYearTagType childYearTagType, int yearOrdinalNumber,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
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
        ChildNextPageSelectorType childNextPageSelectorType,  int nextPageOrdinalNumber)
        throws InterruptedException {

        List<WebElement> yearElements = elementFinderUtil.getYearElements(webDriver,
            parentYearIdentifier,
            parentYearTagType, childYearIdentifier, childYearTagType, yearOrdinalNumber);
        if (yearElements.isEmpty()) {
            throw new WebDriverException(
                "No YearElement found for identifier: " + parentYearIdentifier + " or "
                    + childYearIdentifier);
        }

        List<String> yearLinks = new ArrayList<>();
        for (WebElement yearElement : yearElements) {
            yearLinks.add(yearElement.getAttribute("href"));
        }
/*
        System.out.println("############################");
        for (int i = 0; i < yearElements.size(); i++) {
            System.out.println("yearLinks[" + i + "]: " + yearLinks.get(i));
        }
        System.out.println("############################");
*/
        try {
            int totalYear = elementCountUtil.getTotalYearCnt(yearLinks);

            for (int currentYear = 1; currentYear <= totalYear; currentYear++) {
                Thread.sleep(500);

                if (lstType.equals(LstType.NONE)) {
                    singlePageStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, extendedPdfType, parentExtendedPdfIdentifier,
                        parentExtendedPdfTagType, extendedPdfOrdinalNumber, pdfType,
                        parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                        childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                        parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                        titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
                } else {
                    listedContentStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, lstType, parentLstIdentifier, parentLstTagType,
                        parentLstSelectorType, childLstIdentifier, childLstTagType,
                        childLstSelectorType, lstOrdinalNumber, extendedPdfType,
                        parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                        extendedPdfOrdinalNumber, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                        childPdfSelectorType, pdfOrdinalNumber, titleType, parentTitleIdentifier,
                        parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
                        childTitleTagType, childTitleSelectorType, titleOrdinalNumber, nextPageType,
                        parentNextPageIdentifier, parentNextPageTagType, parentNextPageSelectorType,
                        childNextPageIdentifier, childNextPageTagType, childNextPageSelectorType,
                        nextPageOrdinalNumber);
                }

                if (currentYear < totalYear) {
                    checkOnClickUtil.checkOnClickYear(webDriver, yearType,
                        yearLinks.get(currentYear));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
