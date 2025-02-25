package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.constants.KeywordConstants;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.LstCrawlingService;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickLstUtil {

    private final LstCrawlingService lstCrawlingService;

    public HasOnClickLstUtil(LstCrawlingService lstCrawlingService) {
        this.lstCrawlingService = lstCrawlingService;
    }

    public void goToLstByOnclick(WebDriver webDriver, String pageUrl, Target target,
        ExtendedPdfType extendedPdfType,
        String parentExtendedPdfIdentifier, ParentExtendedPdfTagType parentExtendedPdfTagType,
        String onClickLstScript, PdfType pdfType,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType,
        int pdfOrdinalNumber, TitleType titleType, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, ParentTitleSelectorType parentTitleSelectorType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType,
        ChildTitleSelectorType childTitleSelectorType, int titleOrdinalNumber, String titleText) {

        String lstLink = getLstLink(pageUrl, onClickLstScript);

        if (titleType.equals(TitleType.OUT)) {
            lstCrawlingService.crawlLstWithTitleText(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                lstLink, pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
                childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber,
                titleText);
        } else if (titleType.equals(TitleType.IN)) {
            lstCrawlingService.crawlLst(pageUrl, target, extendedPdfType,
                parentExtendedPdfIdentifier,
                parentExtendedPdfTagType, lstLink, pdfType,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
                childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, parentTitleIdentifier,
                parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
                childTitleTagType, childTitleSelectorType, titleOrdinalNumber);
        }
    }

    private String getLstLink(String pageUrl, String onClickLstScript) {
        try {
            URL url = new URL(pageUrl);
            String baseDomain = url.getProtocol() + "://" + url.getHost();

            if (onClickLstScript.contains(KeywordConstants.ON_CLICK_LST_KEYWORDS[0])) {
                return extractFnView(onClickLstScript, baseDomain);
            } else if (onClickLstScript.contains(KeywordConstants.ON_CLICK_LST_KEYWORDS[1])) {
                return extractFnArticleLink(onClickLstScript, baseDomain);
            } else if (onClickLstScript.contains(KeywordConstants.ON_CLICK_LST_KEYWORDS[2])) {
                return extractGoToView(onClickLstScript, baseDomain, pageUrl);
            } else if (onClickLstScript.contains(KeywordConstants.ON_CLICK_LST_KEYWORDS[3])) {
                return extractGoDetail(onClickLstScript, baseDomain, pageUrl);
            } else {
                throw new WebDriverException("Unsupported onClickLstScript: " + onClickLstScript);
            }
        } catch (Exception e) {
            throw new WebDriverException("Error processing pageUrl: " + pageUrl, e);
        }
    }

    private String extractFnView(String script, String baseDomain) {
        Pattern pattern = Pattern.compile("fn_view\\('([^']+)'\\s*,\\s*'([^']+)'\\)");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() == 2) {
            String nttId = matcher.group(1);
            String bbsId = matcher.group(2);
            return baseDomain + "/board/viewArticle.do?bbsId=" + bbsId + "&nttId=" + nttId + "&pageIndex=1";
        }
        throw new WebDriverException("Failed to extract parameters from fn_view script: " + script);
    }

    private String extractFnArticleLink(String script, String baseDomain) {
        Pattern pattern = Pattern.compile("fn_articleLink\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() >= 1) {
            String boardIdx = matcher.group(1);
            return baseDomain + "/kor/boardView.do?IDX=151&BRD_ID=1018&BOARD_IDX=" + boardIdx +
                "&searchType=ALL&searchValue=&page=1";
        }
        throw new WebDriverException("Failed to extract parameters from fn_articleLink script: " + script);
    }

    private String extractGoToView(String script, String baseDomain, String pageUrl) {
        String endpoint = pageUrl.contains("/ctf/") ? "/ctf/bbs/view.do" : "/portal/bbs/view.do";
        Pattern pattern = Pattern.compile("goTo\\.view\\('list','([^']+)','([^']+)','([^']+)'\\)");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() == 3) {
            String bIdx = matcher.group(1);
            String ptIdx = matcher.group(2);
            String mId = matcher.group(3);
            return baseDomain + endpoint + "?mId=" + mId + "&bIdx=" + bIdx + "&ptIdx=" + ptIdx;
        }
        throw new WebDriverException("Failed to extract parameters from goTo.view script: " + script);
    }

    private String extractGoDetail(String script, String baseDomain, String pageUrl) {
        Pattern pattern = Pattern.compile("goDetail\\('([^']+)'\\)");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() >= 1) {
            String idx = matcher.group(1);
            String mId = "";
            String query = null;
            try {
                query = new URL(pageUrl).getQuery();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            if (query != null && query.contains("mId=")) {
                String[] params = query.split("&");
                for (String param : params) {
                    if (param.startsWith("mId=")) {
                        mId = param.substring(4);
                        break;
                    }
                }
            }
            return baseDomain + "/portal/ebook/siboDetail.do?mId=" + mId + "&idx=" + idx;
        }
        throw new WebDriverException("Failed to extract parameters from goDetail script: " + script);
    }
}
