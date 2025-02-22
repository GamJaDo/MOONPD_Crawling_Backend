package Intern.moonpd_crawling.util.pdfCrawling;

import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class NoOnclickPdfUtil {

    private final ElementFinderUtil elementFinderUtil;

    public NoOnclickPdfUtil(ElementFinderUtil elementFinderUtil) {
        this.elementFinderUtil = elementFinderUtil;
    }

    public String getPdfLink(WebDriver webDriver, List<WebElement> pdfLinks,
        ChildPdfTagType childPdfTagType, int index) {

        return elementFinderUtil.getPdfLink(webDriver, pdfLinks, childPdfTagType, index);
    }
}
