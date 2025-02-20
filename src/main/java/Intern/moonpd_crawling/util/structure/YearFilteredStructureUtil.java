package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.YearType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.child.ChildYearTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentYearTagType;
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

    public void crawl(WebDriver webDriver, String pageUrl, Target target, LstType lstType,
        String parentLstIdentifier, ParentLstTagType parentLstTagType,
        String childLstIdentifier, ChildLstTagType childLstTagType, int lstOrdinalNumber,
        YearType yearType, String parentYearIdentifier, ParentYearTagType parentYearTagType,
        String childYearIdentifier, ChildYearTagType childYearTagType, int yearOrdinalNumber,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, int pdfOrdinalNumber,
        TitleType titleType, String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType, int nextPageOrdinalNumber)
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
                    singlePageStructureUtil.crawl(webDriver, pageUrl, target, extendedPdfType,
                        parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                        extendedPdfOrdinalNumber, pdfType, parentPdfIdentifier, parentPdfTagType,
                        childPdfIdentifier, childPdfTagType, pdfOrdinalNumber,
                        parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
                        childTitleTagType, titleOrdinalNumber, nextPageType,
                        parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
                        childNextPageTagType, nextPageOrdinalNumber);
                } else {
                    listedContentStructureUtil.crawl(webDriver, pageUrl, target, lstType,
                        parentLstIdentifier, parentLstTagType, childLstIdentifier, childLstTagType,
                        lstOrdinalNumber, extendedPdfType, parentExtendedPdfIdentifier,
                        parentExtendedPdfTagType, extendedPdfOrdinalNumber, pdfType,
                        parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                        pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
                        childTitleIdentifier, childTitleTagType, titleOrdinalNumber, nextPageType,
                        parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
                        childNextPageTagType, nextPageOrdinalNumber);
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
