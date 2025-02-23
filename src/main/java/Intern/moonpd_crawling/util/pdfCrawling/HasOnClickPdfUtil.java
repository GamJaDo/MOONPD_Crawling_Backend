package Intern.moonpd_crawling.util.pdfCrawling;

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

            if (onClickPdfScript.contains("fn_egov_downFile")) {
                return extractByParams(
                    onClickPdfScript,
                    "fn_egov_downFile\\('([^']+)'\\s*,\\s*'([^']+)'\\)",
                    baseDomain, "/cmm/fms/FileDown.do",
                    "atchFileId", "fileSn");
            } else if (onClickPdfScript.contains("yhLib.file.download")) {
                return extractByParams(
                    onClickPdfScript,
                    "yhLib\\.file\\.download\\('([^']+)'\\s*,\\s*'([^']+)'\\)",
                    baseDomain, "/common/file/download.do",
                    "atchFileId", "fileSn");
            } else if (onClickPdfScript.contains("location.href=")) {
                return extractLocationHref(onClickPdfScript, baseDomain);
            } else if (onClickPdfScript.contains("pdf_download(")) {
                return extractByParams(
                    onClickPdfScript,
                    "pdf_download\\('([^']+)'\\s*,\\s*'([^']+)'\\s*,\\s*'([^']+)'\\)",
                    baseDomain, "/fnc_bbs/user_bbs_download",
                    "bcd", "bn", "num");
            } else if (onClickPdfScript.contains("openDownloadFiles(")) {
                return extractOpenDownloadFiles(onClickPdfScript, baseDomain);
            } else {
                throw new WebDriverException(
                    "Unknown PDF download function in script: " + onClickPdfScript);
            }
        } catch (Exception e) {
            throw new WebDriverException("Error processing pageUrl: " + pageUrl, e);
        }
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
        throw new WebDriverException(
            "Failed to extract parameters using regex: " + regex + " from script: " + script);
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
        throw new WebDriverException(
            "Failed to extract parameter from openDownloadFiles script: " + script);
    }
}
