package Intern.moonpd_crawling.util.pdfCrawling;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class NoOnclickPdfUtil {

    public String getPdfLinkByHref(List<WebElement> pdfLinks, int index) {

        return pdfLinks.get(index).getAttribute("href");
    }
}
