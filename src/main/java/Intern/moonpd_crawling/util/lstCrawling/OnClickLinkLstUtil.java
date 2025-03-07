package Intern.moonpd_crawling.util.lstCrawling;

import Intern.moonpd_crawling.constants.KeywordConstants;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.service.CrawlDetailPageService;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Component;

@Component
public class OnClickLinkLstUtil {

    private final CrawlDetailPageService crawlDetailPageService;

    public OnClickLinkLstUtil(CrawlDetailPageService crawlDetailPageService) {
        this.crawlDetailPageService = crawlDetailPageService;
    }

    public void goToLstByOnclick(String pageUrl, Target target, LinkType lstType,
        ExtendedType extendedPdfType, String extendedPdfIdentifier, TagType extendedPdfTagType,
        SelectorType extendedPdfSelectorType, String onClickLstScript, LinkType pdfType,
        String parentPdfIdentifier, TagType parentPdfTagType, SelectorType parentPdfSelectorType,
        String childPdfIdentifier, TagType childPdfTagType, SelectorType childPdfSelectorType,
        int pdfOrdinalNumber, ExtendedType extendedTitleType, String extendedTitleIdentifier,
        TagType extendedTitleTagType, SelectorType extendedTitleSelectorType,  TitleType titleType,
        String parentTitleIdentifier, TagType parentTitleTagType, SelectorType parentTitleSelectorType,
        String childTitleIdentifier, TagType childTitleTagType, SelectorType childTitleSelectorType,
        int titleOrdinalNumber, String titleText) {

        String lstLink = getLstLink(pageUrl, onClickLstScript);

        crawlDetailPageService.crawlSubPage(pageUrl, target, lstType, extendedPdfType,
            extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, lstLink, pdfType,
            parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, childPdfIdentifier,
            childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, extendedTitleType,
            extendedTitleIdentifier, extendedTitleTagType, extendedTitleSelectorType,  titleType,
            parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber, titleText);
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
            } else if (onClickLstScript.contains(KeywordConstants.ON_CLICK_LST_KEYWORDS[4])) {
                return extractFnSearchDetail(onClickLstScript, baseDomain);
            }
            else {
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

    private String extractFnSearchDetail(String script, String baseDomain) {
        Pattern pattern = Pattern.compile("fn_search_detail\\('([^']+)'\\)");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() >= 1) {
            String nttId = matcher.group(1);
            return baseDomain + "/prog/bbsArticle/BBSMSTR_000000000191/view.do?nttId=" + nttId;
        }
        throw new WebDriverException("Failed to extract parameters from fn_search_detail script: " + script);
    }
}
