package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public NoOnClickLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToLstByHref(String pageUrl, Target target, LstType lstType, ExtendedPdfType extendedPdfType,
        String extendedPdfIdentifier, ExtendedPdfTagType extendedPdfTagType,
        ExtendedPdfSelectorType extendedPdfSelectorType, String lstLink, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber) {


        lstCrawlingService.crawlLst(pageUrl, target, lstType, extendedPdfType,
            extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
            childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier,
            parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber);

    }
}
