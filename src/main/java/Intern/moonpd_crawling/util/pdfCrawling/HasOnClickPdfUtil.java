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
            String regex;
            String endpoint = "";

            if (onClickPdfScript.contains("fn_egov_downFile")) {
                regex = "fn_egov_downFile\\('([^']+)'\\s*,\\s*'([^']+)'\\)";
                endpoint = "/cmm/fms/FileDown.do";
            } else if (onClickPdfScript.contains("yhLib.file.download")) {
                regex = "yhLib\\.file\\.download\\('([^']+)'\\s*,\\s*'([^']+)'\\)";
                endpoint = "/common/file/download.do";
            } else if (onClickPdfScript.contains("location.href=")) {
                regex = "location\\.href=['\"]([^'\"]+)['\"]";
            } else {
                throw new WebDriverException("Unknown PDF download function in script: " + onClickPdfScript);
            }

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(onClickPdfScript);
            if (matcher.find() && matcher.groupCount() >= 1) {
                if (endpoint.isEmpty()) {
                    String extractedUrl = matcher.group(1).replaceAll("&amp;", "&");
                    return baseDomain + extractedUrl;
                } else {
                    if (matcher.groupCount() < 2) {
                        throw new WebDriverException("Failed to extract both parameters using regex: " + regex + " from script: " + onClickPdfScript);
                    }
                    String atchFileId = matcher.group(1);
                    String fileSn = matcher.group(2);
                    return baseDomain + endpoint + "?atchFileId=" + atchFileId + "&fileSn=" + fileSn;
                }
            } else {
                throw new WebDriverException("Failed to extract parameters using regex: " + regex + " from script: " + onClickPdfScript);
            }
        } catch (Exception e) {
            throw new WebDriverException("Error processing pageUrl: " + pageUrl, e);
        }
    }
}
