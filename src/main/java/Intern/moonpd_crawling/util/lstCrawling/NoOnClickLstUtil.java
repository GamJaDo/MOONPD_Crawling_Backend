package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public NoOnClickLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToLstByHref(String pageUrl, Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, String lstLink, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        String titleText) {

        if (titleType.equals(TitleType.OUT)) {
            lstCrawlingService.crawlLstWithTitle(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                childPdfTagType, pdfOrdinalNumber, titleText);
        } else if (titleType.equals(TitleType.IN)) {
            lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier,
                childPdfTagType, pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
                childTitleIdentifier, childTitleTagType, titleOrdinalNumber);
        }
    }
}
