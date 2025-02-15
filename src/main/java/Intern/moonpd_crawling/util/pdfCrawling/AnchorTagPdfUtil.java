package Intern.moonpd_crawling.util.pdfCrawling;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class AnchorTagPdfUtil {

    public String getPdfLink(List<WebElement> pdfLinks, int currentPage) {

        return pdfLinks.get(currentPage).getAttribute("href");
    }
}