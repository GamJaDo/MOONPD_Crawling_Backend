package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public NoOnClickLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToLstByElement(Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, String lstLink,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        int pdfOrdinalNumber, String titleText) {

        lstCrawlingService.crawlLst(target, extendedPdfType, parentExtendedPdfIdentifier,
            parentExtendedPdfTagType,
            extendedPdfOrdinalNumber, lstLink,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, titleText);
    }
}
