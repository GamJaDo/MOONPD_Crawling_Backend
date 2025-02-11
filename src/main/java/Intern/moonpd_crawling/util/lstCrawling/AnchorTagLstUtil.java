package Intern.moonpd_crawling.util.lstCrawling;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class AnchorTagLstUtil {

    public String getLstLink(List<WebElement> lstLinks, int currentPage) {

        return lstLinks.get(currentPage).getAttribute("href");
    }
}
