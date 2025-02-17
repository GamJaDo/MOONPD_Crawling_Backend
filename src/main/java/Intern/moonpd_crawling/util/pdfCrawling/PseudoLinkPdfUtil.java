package Intern.moonpd_crawling.util.pdfCrawling;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class PseudoLinkPdfUtil {

    public String getPdfLinkByPseudoLink(String pageUrl, WebElement pseudoLinkElement) {
        try {
            String onclick = pseudoLinkElement.getAttribute("onclick");

            if (onclick == null || onclick.trim().isEmpty()) {
                throw new WebDriverException("Onclick attribute is empty in pseudo link element.");
            }
            Pattern pattern = Pattern.compile("downloadPdf\\((\\d+)\\)");
            Matcher matcher = pattern.matcher(onclick);
            if (!matcher.find()) {
                throw new WebDriverException("Unable to extract idx from pseudo link onclick attribute: " + onclick);
            }
            String idx = matcher.group(1);
            URL url = new URL(pageUrl);
            String baseUrl = url.getProtocol() + "://" + url.getHost();
            String endpoint = "/_prog/common/download_pdf.asp";

            return baseUrl + endpoint + "?idx=" + idx;
        } catch (Exception e) {
            throw new WebDriverException("Error processing pseudo link element.", e);
        }
    }
}
