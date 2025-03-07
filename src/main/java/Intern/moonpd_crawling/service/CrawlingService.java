package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.TargetRepository;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.type.StructureType;
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
        List<Target> targetComponent = targetRepository.findAll().stream().toList();

        System.setProperty("webdriver.chrome.driver",
            "C:\\tools\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--disable-popup-blocking");

        WebDriver webDriver = new ChromeDriver(options);

        try {
            for (Target target : targetComponent) {
                StructureType structureType = target.getStructureType();

                ExtendedType extendedLstType = target.getExtendedLstType();
                String extendedLstIdentifier = target.getExtendedLstIdentifier();
                TagType extendedLstTagType = target.getExtendedLstTagType();
                SelectorType extendedLstSelectorType = target.getExtendedLstSelectorType();

                LinkType lstType = target.getLstType();
                String parentLstIdentifier = target.getParentLstIdentifier();
                TagType parentLstTagType = target.getParentLstTagType();
                SelectorType parentLstSelectorType = target.getParentLstSelectorType();
                String childLstIdentifier = target.getChildLstIdentifier();
                TagType childLstTagType = target.getChildLstTagType();
                SelectorType childLstSelectorType = target.getChildLstSelectorType();
                int lstOrdinalNumber = target.getLstOrdinalNumber();

                LinkType yearType = target.getYearType();
                String parentYearIdentifier = target.getParentYearIdentifier();
                TagType parentYearTagType = target.getParentYearTagType();
                SelectorType parentYearSelectorType = target.getParentYearSelectorType();
                String childYearIdentifier = target.getChildYearIdentifier();
                TagType childYearTagType = target.getChildYearTagType();
                SelectorType childYearSelectorType = target.getChildYearSelectorType();
                int yearOrdinalNumber = target.getYearOrdinalNumber();

                String pageUrl = target.getPageUrl();
                webDriver.get(pageUrl);
                Thread.sleep(1000);

                int totalPage = target.getTotalPage();

                ExtendedType extendedPdfType = target.getExtendedPdfType();
                String extendedPdfIdentifier = target.getExtendedPdfIdentifier();
                TagType extendedPdfTagType = target.getExtendedPdfTagType();
                SelectorType extendedPdfSelectorType = target.getExtendedPdfSelectorType();

                LinkType pdfType = target.getPdfType();
                String parentPdfIdentifier = target.getParentPdfIdentifier();
                TagType parentPdfTagType = target.getParentPdfTagType();
                SelectorType parentPdfSelectorType = target.getParentPdfSelectorType();
                String childPdfIdentifier = target.getChildPdfIdentifier();
                TagType childPdfTagType = target.getChildPdfTagType();
                SelectorType childPdfSelectorType = target.getChildPdfSelectorType();
                int pdfOrdinalNumber = target.getPdfOrdinalNumber();

                ExtendedType extendedTitleType = target.getExtendedTitleType();
                String extendedTitleIdentifier = target.getExtendedTitleIdentifier();
                TagType extendedTitleTagType = target.getExtendedTitleTagType();
                SelectorType extendedTitleSelectorType = target.getExtendedTitleSelectorType();

                TitleType titleType = target.getTitleType();
                String parentTitleIdentifier = target.getParentTitleIdentifier();
                TagType parentTitleTagType = target.getParentTitleTagType();
                SelectorType parentTitleSelectorType = target.getParentTitleSelectorType();
                String childTitleIdentifier = target.getChildTitleIdentifier();
                TagType childTitleTagType = target.getChildTitleTagType();
                SelectorType childTitleSelectorType = target.getChildTitleSelectorType();
                int titleOrdinalNumber = target.getTitleOrdinalNumber();

                LinkType nextPageType = target.getNextPageType();
                String parentNextPageIdentifier = target.getParentNextPageIdentifier();
                TagType parentNextPageTagType = target.getParentNextPageTagType();
                SelectorType parentNextPageSelectorType = target.getParentNextPageSelectorType();
                String childNextPageIdentifier = target.getChildNextPageIdentifier();
                TagType childNextPageTagType = target.getChildNextPageTagType();
                SelectorType childNextPageSelectorType = target.getChildNextPageSelectorType();
                int nextPageOrdinalNumber = target.getNextPageOrdinalNumber();

                if (structureType.equals(StructureType.SINGLE_PAGE)) {
                    singlePageStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, lstType, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
                        extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                        pdfOrdinalNumber, extendedTitleType, extendedTitleIdentifier, extendedTitleTagType,
                        extendedTitleSelectorType, titleType, parentTitleIdentifier, parentTitleTagType,
                        parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                        childTitleSelectorType, titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
                } else if (structureType.equals(StructureType.YEAR_FILTERED)) {
                    yearFilteredStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, extendedLstType, extendedLstIdentifier, extendedLstTagType,
                        extendedLstSelectorType, lstType, parentLstIdentifier, parentLstTagType,
                        parentLstSelectorType, childLstIdentifier, childLstTagType,
                        childLstSelectorType, lstOrdinalNumber, yearType, parentYearIdentifier,
                        parentYearTagType, parentYearSelectorType, childYearIdentifier, childYearTagType,
                        childYearSelectorType, yearOrdinalNumber, extendedPdfType, extendedPdfIdentifier,
                        extendedPdfTagType, extendedPdfSelectorType, pdfType, parentPdfIdentifier,
                        parentPdfTagType, parentPdfSelectorType, childPdfIdentifier, childPdfTagType,
                        childPdfSelectorType, pdfOrdinalNumber, extendedTitleType, extendedTitleIdentifier,
                        extendedTitleTagType, extendedTitleSelectorType, titleType, parentTitleIdentifier,
                        parentTitleTagType, parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                        childTitleSelectorType, titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
                } else if (structureType.equals(StructureType.LISTED_CONTENT)) {
                    listedContentStructureUtil.crawl(webDriver, structureType, pageUrl, totalPage,
                        target, extendedLstType, extendedLstIdentifier, extendedLstTagType,
                        extendedLstSelectorType, lstType, parentLstIdentifier, parentLstTagType,
                        parentLstSelectorType, childLstIdentifier, childLstTagType, childLstSelectorType,
                        lstOrdinalNumber, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
                        extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType,
                        parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
                        pdfOrdinalNumber, extendedTitleType, extendedTitleIdentifier, extendedTitleTagType,
                        extendedTitleSelectorType, titleType, parentTitleIdentifier, parentTitleTagType,
                        parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
                        childTitleSelectorType, titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
                        parentNextPageTagType, parentNextPageSelectorType, childNextPageIdentifier,
                        childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
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
