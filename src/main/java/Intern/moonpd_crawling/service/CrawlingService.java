package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.TargetRepository;
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
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.YearType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.child.ChildYearTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentYearTagType;
import Intern.moonpd_crawling.util.structure.ListedContentStructureUtil;
import Intern.moonpd_crawling.util.structure.SinglePageStructureUtil;
import Intern.moonpd_crawling.util.structure.YearFilteredStructureUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class CrawlingService {

    private final TargetRepository targetRepository;
    private final SinglePageStructureUtil singlePageStructureUtil;
    private final YearFilteredStructureUtil yearFilteredStructureUtil;
    private final ListedContentStructureUtil listedContentStructureUtil;

    public CrawlingService(TargetRepository targetRepository,
        SinglePageStructureUtil singlePageStructureUtil,
        YearFilteredStructureUtil yearFilteredStructureUtil,
        ListedContentStructureUtil listedContentStructureUtil
    ) {
        this.targetRepository = targetRepository;
        this.singlePageStructureUtil = singlePageStructureUtil;
        this.yearFilteredStructureUtil = yearFilteredStructureUtil;
        this.listedContentStructureUtil = listedContentStructureUtil;
    }

    public void startCrawling() {
        List<Target> targetList = targetRepository.findAll().stream().toList();

        System.setProperty("webdriver.chrome.driver",
            "C:\\tools\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        try {
            for (Target target : targetList) {
                StructureType structureType = target.getStructureType();

                ExtendedLstType extendedLstType = target.getExtendedLstType();
                ExtendedLstTagType extendedLstTagType = target.getExtendedLstTagType();
                String extendedLstIdentifier = target.getExtendedLstIdentifier();
                ExtendedLstSelectorType extendedLstSelectorType = target.getExtendedLstSelectorType();

                LstType lstType = target.getLstType();
                String parentLstIdentifier = target.getParentLstIdentifier();
                ParentLstTagType parentLstTagType = target.getParentLstTagType();
                ParentLstSelectorType parentLstSelectorType = target.getParentLstSelectorType();
                String childLstIdentifier = target.getChildLstIdentifier();
                ChildLstTagType childLstTagType = target.getChildLstTagType();
                ChildLstSelectorType childLstSelectorType = target.getChildLstSelectorType();
                int lstOrdinalNumber = target.getLstOrdinalNumber();

                YearType yearType = target.getYearType();
                String parentYearIdentifier = target.getParentYearIdentifier();
                ParentYearTagType parentYearTagType = target.getParentYearTagType();
                String childYearIdentifier = target.getChildYearIdentifier();
                ChildYearTagType childYearTagType = target.getChildYearTagType();
                int yearOrdinalNumber = target.getYearOrdinalNumber();

                String pageUrl = target.getPageUrl();
                webDriver.get(pageUrl);
                Thread.sleep(1000);

                int totalPage = target.getTotalPage();

                ExtendedPdfType extendedPdfType = target.getExtendedPdfType();
                String extendedPdfIdentifier = target.getExtendedPdfIdentifier();
                ExtendedPdfTagType extendedPdfTagType = target.getExtendedPdfTagType();
                ExtendedPdfSelectorType extendedPdfSelectorType = target.getExtendedPdfSelectorType();

                PdfType pdfType = target.getPdfType();
                String parentPdfIdentifier = target.getParentPdfIdentifier();
                ParentPdfTagType parentPdfTagType = target.getParentPdfTagType();
                ParentPdfSelectorType parentPdfSelectorType = target.getParentPdfSelectorType();
                String childPdfIdentifier = target.getChildPdfIdentifier();
                ChildPdfTagType childPdfTagType = target.getChildPdfTagType();
                ChildPdfSelectorType childPdfSelectorType = target.getChildPdfSelectorType();
                int pdfOrdinalNumber = target.getPdfOrdinalNumber();

                String parentTitleIdentifier = target.getParentTitleIdentifier();
                ParentTitleTagType parentTitleTagType = target.getParentTitleTagType();
                ParentTitleSelectorType parentTitleSelectorType = target.getParentTitleSelectorType();
                String childTitleIdentifier = target.getChildTitleIdentifier();
                ChildTitleTagType childTitleTagType = target.getChildTitleTagType();
                ChildTitleSelectorType childTitleSelectorType = target.getChildTitleSelectorType();
                int titleOrdinalNumber = target.getTitleOrdinalNumber();

                NextPageType nextPageType = target.getNextPageType();
                String parentNextPageIdentifier = target.getParentNextPageIdentifier();
                ParentNextPageTagType parentNextPageTagType = target.getParentNextPageTagType();
                ParentNextPageSelectorType parentNextPageSelectorType = target.getParentNextPageSelectorType();
                String childNextPageIdentifier = target.getChildNextPageIdentifier();
                ChildNextPageTagType childNextPageTagType = target.getChildNextPageTagType();
                ChildNextPageSelectorType childNextPageSelectorType = target.getChildNextPageSelectorType();
                int nextPageOrdinalNumber = target.getNextPageOrdinalNumber();

                if (structureType.equals(StructureType.SINGLE_PAGE)) {
                    singlePageStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, lstType, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
                        extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                        pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType,
                        titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
                } else if (structureType.equals(StructureType.YEAR_FILTERED)) {
                    yearFilteredStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, extendedLstType, extendedLstIdentifier, extendedLstTagType,
                        extendedLstSelectorType, lstType, parentLstIdentifier, parentLstTagType,
                        parentLstSelectorType, childLstIdentifier, childLstTagType,
                        childLstSelectorType, lstOrdinalNumber, yearType, parentYearIdentifier,
                        parentYearTagType, childYearIdentifier, childYearTagType, yearOrdinalNumber,
                        extendedPdfType, extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType,
                        pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                        childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                        parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType, titleOrdinalNumber,
                        nextPageType, parentNextPageIdentifier, parentNextPageTagType,
                        parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
                        childNextPageSelectorType, nextPageOrdinalNumber);
                } else if (structureType.equals(StructureType.LISTED_CONTENT)) {
                    listedContentStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, extendedLstType, extendedLstIdentifier, extendedLstTagType,
                        extendedLstSelectorType, lstType, parentLstIdentifier, parentLstTagType,
                        parentLstSelectorType, childLstIdentifier, childLstTagType, childLstSelectorType,
                        lstOrdinalNumber, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
                        extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                        pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType,
                        childTitleIdentifier, childTitleTagType, childTitleSelectorType, titleOrdinalNumber,
                        nextPageType, parentNextPageIdentifier, parentNextPageTagType,
                        parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
                        childNextPageSelectorType, nextPageOrdinalNumber);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        } finally {
            webDriver.quit();
        }
    }
}
