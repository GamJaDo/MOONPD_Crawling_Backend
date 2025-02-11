package Intern.moonpd_crawling.util.pdfCrawling;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ButtonTagPdfUtil {

    public String getPdfLink(List<WebElement> pdfLinks, int currentPage) {
        WebElement buttonElement = pdfLinks.get(currentPage).findElement(By.tagName("button"));
        String onclickValue = buttonElement.getAttribute("onclick");
        return extractUrlFromOnclick(onclickValue);
    }

    private String extractUrlFromOnclick(String onclickValue) {
        if (onclickValue == null || !onclickValue.contains("location.href=")) {
            return null;
        }
        int start = onclickValue.indexOf("'") + 1;
        int end = onclickValue.lastIndexOf("'");
        if (start > 0 && end > start) {
            return onclickValue.substring(start, end);
        }
        return null;
    }
}
