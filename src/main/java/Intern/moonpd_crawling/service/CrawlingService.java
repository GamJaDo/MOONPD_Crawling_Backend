package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.repository.TargetRepository;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.YearType;
import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.child.ChildYearTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.StructureType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentYearTagType;
import Intern.moonpd_crawling.util.structure.ListedContentStructureUtil;
import Intern.moonpd_crawling.util.structure.SinglePageStructureUtil;
import Intern.moonpd_crawling.util.structure.YearFilteredStructureUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

        WebDriver webDriver = new ChromeDriver();
        try {
            for (Target target : targetList) {
                StructureType structureType = target.getStructureType();

                LstType lstType = target.getLstType();
                String parentLstIdentifier = target.getParentLstIdentifier();
                ParentLstTagType parentLstTagType = target.getParentLstTagType();
                String childLstIdentifier = target.getChildLstIdentifier();
                ChildLstTagType childLstTagType = target.getChildLstTagType();
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

                ExtendedPdfType extendedPdfType = target.getExtendedPdfType();
                String parentExtendedPdfIdentifier = target.getParentExtendedPdfIdentifier();
                ParentExtendedPdfTagType parentExtendedPdfTagType = target.getParentExtendedPdfTagType();
                int extendedPdfOrdinalNumber = target.getExtendedPdfOrdinalNumber();

                PdfType pdfType = target.getPdfType();
                String parentPdfIdentifier = target.getParentPdfIdentifier();
                ParentPdfTagType parentPdfTagType = target.getParentPdfTagType();
                String childPdfIdentifier = target.getChildPdfIdentifier();
                ChildPdfTagType childPdfTagType = target.getChildPdfTagType();
                int pdfOrdinalNumber = target.getPdfOrdinalNumber();

                String parentTitleIdentifier = target.getParentTitleIdentifier();
                ParentTitleTagType parentTitleTagType = target.getParentTitleTagType();
                String childTitleIdentifier = target.getChildTitleIdentifier();
                ChildTitleTagType childTitleTagType = target.getChildTitleTagType();
                int titleOrdinalNumber = target.getTitleOrdinalNumber();

                NextPageType nextPageType = target.getNextPageType();
                String parentNextPageIdentifier = target.getParentNextPageIdentifier();
                ParentNextPageTagType parentNextPageTagType = target.getParentNextPageTagType();
                String childNextPageIdentifier = target.getChildNextPageIdentifier();
                ChildNextPageTagType childNextPageTagType = target.getChildNextPageTagType();
                int nextPageOrdinalNumber = target.getNextPageOrdinalNumber();

                if (structureType.equals(StructureType.SINGLE_PAGE)) {
                    singlePageStructureUtil.crawl(webDriver, pageUrl, target, extendedPdfType,
                        parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                        extendedPdfOrdinalNumber, pdfType, parentPdfIdentifier, parentPdfTagType,
                        childPdfIdentifier, childPdfTagType, pdfOrdinalNumber,
                        parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
                        childTitleTagType, titleOrdinalNumber, nextPageType,
                        parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
                        childNextPageTagType, nextPageOrdinalNumber);
                } else if (structureType.equals(StructureType.YEAR_FILTERED)) {
                    yearFilteredStructureUtil.crawl(webDriver, pageUrl, target, yearType,
                        parentYearIdentifier, parentYearTagType, childYearIdentifier,
                        childYearTagType, yearOrdinalNumber, extendedPdfType,
                        parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                        extendedPdfOrdinalNumber, pdfType, parentPdfIdentifier, parentPdfTagType,
                        childPdfIdentifier, childPdfTagType, pdfOrdinalNumber,
                        parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
                        childTitleTagType, titleOrdinalNumber, nextPageType,
                        parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
                        childNextPageTagType, nextPageOrdinalNumber);
                } else if (structureType.equals(StructureType.LISTED_CONTENT)) {
                    listedContentStructureUtil.crawl(webDriver, pageUrl, target, lstType,
                        parentLstIdentifier, parentLstTagType, childLstIdentifier, childLstTagType,
                        lstOrdinalNumber, extendedPdfType, parentExtendedPdfIdentifier,
                        parentExtendedPdfTagType, extendedPdfOrdinalNumber, pdfType,
                        parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
                        pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                        childTitleIdentifier, childTitleTagType, titleOrdinalNumber, nextPageType,
                        parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
                        childNextPageTagType, nextPageOrdinalNumber);
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
