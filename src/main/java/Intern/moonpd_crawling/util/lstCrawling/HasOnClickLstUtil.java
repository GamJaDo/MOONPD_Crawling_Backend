package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public HasOnClickLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToLstByOnclickWithTitle(String pageUrl, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, Target target, String onClickLstScript, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String titleText) {

        String lstLink = getLstLink(pageUrl, onClickLstScript);

        lstCrawlingService.crawlLstWithTitle(pageUrl, target, extendedPdfType, parentExtendedPdfIdentifier,
            parentExtendedPdfTagType,
            extendedPdfOrdinalNumber, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, titleText);
    }

    public void goToLstByOnclick(String pageUrl, Target target, ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        int extendedPdfOrdinalNumber, String onClickLstScript, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, int titleOrdinalNumber) {

        String lstLink = getLstLink(pageUrl, onClickLstScript);

        lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType, parentExtendedPdfIdentifier,
            parentExtendedPdfTagType, extendedPdfOrdinalNumber, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, parentTitleIdentifier, parentTitleTagType, childTitleIdentifier,
            childTitleTagType, titleOrdinalNumber);
    }

    private String getLstLink(String pageUrl, String onClickLstScript) {
        try {
            URL url = new URL(pageUrl);
            String baseDomain = url.getProtocol() + "://" + url.getHost();
            String endpoint = "/portal/bbs/view.do";
            Pattern pattern = Pattern.compile(
                "goTo\\.view\\('list','([^']+)','([^']+)','([^']+)'\\)");
            Matcher matcher = pattern.matcher(onClickLstScript);
            if (matcher.find() && matcher.groupCount() == 3) {
                String bIdx = matcher.group(1);
                String ptIdx = matcher.group(2);
                String mId = matcher.group(3);

                return baseDomain + endpoint + "?mId=" + mId + "&bIdx=" + bIdx + "&ptIdx=" + ptIdx;
            } else {
                throw new WebDriverException(
                    "Failed to extract parameters from onClick script: " + onClickLstScript);
            }
        } catch (Exception e) {
            throw new WebDriverException("Error processing pageUrl: " + pageUrl);
        }
    }
}
