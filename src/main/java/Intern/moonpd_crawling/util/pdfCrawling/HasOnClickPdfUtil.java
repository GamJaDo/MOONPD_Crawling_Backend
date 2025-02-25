package Intern.moonpd_crawling.util.pdfCrawling;

import Intern.moonpd_crawling.constants.KeywordConstants;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriverException;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickPdfUtil {

    public String getPdfLinkByOnClick(String pageUrl, String onClickPdfScript) {
        try {
            URL url = new URL(pageUrl);
            String baseDomain = url.getProtocol() + "://" + url.getHost();

            if (onClickPdfScript.contains(KeywordConstants.ON_CLICK_PDF_KEYWORDS[0])) {
                return extractEgovDownFile(onClickPdfScript, baseDomain);
            } else if (onClickPdfScript.contains(KeywordConstants.ON_CLICK_PDF_KEYWORDS[1])) {
                return extractYhLibFileDownload(onClickPdfScript, baseDomain);
            } else if (onClickPdfScript.contains(KeywordConstants.ON_CLICK_PDF_KEYWORDS[2])) {
                return extractLocationHref(onClickPdfScript, baseDomain);
            } else if (onClickPdfScript.contains(KeywordConstants.ON_CLICK_PDF_KEYWORDS[3])) {
                return extractPdfDownload(onClickPdfScript, baseDomain);
            } else if (onClickPdfScript.contains(KeywordConstants.ON_CLICK_PDF_KEYWORDS[4])) {
                return extractOpenDownloadFiles(onClickPdfScript, baseDomain);
            } else if (onClickPdfScript.contains(KeywordConstants.ON_CLICK_PDF_KEYWORDS[5])) {
                return extractGfnFileDownload(onClickPdfScript, baseDomain);
            } else {
                throw new WebDriverException("Unknown PDF download function in script: " + onClickPdfScript);
            }
        } catch (Exception e) {
            throw new WebDriverException("Error processing pageUrl: " + pageUrl, e);
        }
    }

    private String extractEgovDownFile(String script, String baseDomain) {
        return extractByParams(
            script,
            "fn_egov_downFile\\('([^']+)'\\s*,\\s*'([^']+)'\\)",
            baseDomain, "/cmm/fms/FileDown.do",
            "atchFileId", "fileSn"
        );
    }

    private String extractYhLibFileDownload(String script, String baseDomain) {
        return extractByParams(
            script,
            "yhLib\\.file\\.download\\('([^']+)'\\s*,\\s*'([^']+)'\\)",
            baseDomain, "/common/file/download.do",
            "atchFileId", "fileSn"
        );
    }

    private String extractPdfDownload(String script, String baseDomain) {
        return extractByParams(
            script,
            "pdf_download\\('([^']+)'\\s*,\\s*'([^']+)'\\s*,\\s*'([^']+)'\\)",
            baseDomain, "/fnc_bbs/user_bbs_download",
            "bcd", "bn", "num"
        );
    }

    private String extractGfnFileDownload(String script, String baseDomain) {
        return extractByParams(
            script,
            "gfnFileDownload\\('([^']+)'\\s*,\\s*'([^']+)'\\)",
            baseDomain, "/cmmn/file/download",
            "fileNo", "fileNm"
        );
    }

    private String extractLocationHref(String script, String baseDomain) {
        Pattern pattern = Pattern.compile("location\\.href=['\"]([^'\"]+)['\"]");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() >= 1) {
            String extractedUrl = matcher.group(1).replaceAll("&amp;", "&");
            return baseDomain + extractedUrl;
        }
        throw new WebDriverException("Failed to extract location.href from script: " + script);
    }

    private String extractOpenDownloadFiles(String script, String baseDomain) {
        Pattern pattern = Pattern.compile("openDownloadFiles\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() >= 1) {
            String uid = matcher.group(1);
            return baseDomain + "/programs/board/download.do?parm_file_uid=" + uid;
        }
        throw new WebDriverException("Failed to extract parameter from openDownloadFiles script: " + script);
    }

    private String extractByParams(String script, String regex, String baseDomain, String endpoint,
        String... paramNames) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(script);
        if (matcher.find() && matcher.groupCount() >= paramNames.length) {
            StringBuilder sb = new StringBuilder(baseDomain);
            sb.append(endpoint).append("?");
            for (int i = 0; i < paramNames.length; i++) {
                if (i > 0) {
                    sb.append("&");
                }
                sb.append(paramNames[i]).append("=").append(matcher.group(i + 1));
            }
            return sb.toString();
        }
        throw new WebDriverException("Failed to extract parameters using regex: " + regex + " from script: " + script);
    }
}
