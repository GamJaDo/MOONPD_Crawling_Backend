package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.util.pdfCrawling.HasOnClickPdfUtil;
import Intern.moonpd_crawling.util.pdfCrawling.NoOnclickPdfUtil;
import Intern.moonpd_crawling.util.pdfCrawling.PseudoLinkPdfUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

/*
    SinglePage 같은 경우 pdf는 페이지 내에서 바로 크롤링을 처리하지만
    ListedContent 같은 경우 lst 로 들어간 다음 pdf를 크롤링 하기에 구조상 pdf의 onclick 체크는
    CheckOnClickUtil에서 진행을 할 수 없음(순환 참조 문제)
    그렇기에 이렇게 class를 별도로 분리해서 사용함
 */

@Component
public class CheckOnClickPdfUtil {

    private final HasOnClickPdfUtil hasOnClickPdfUtil;
    private final NoOnclickPdfUtil noOnclickPdfUtil;
    private final PseudoLinkPdfUtil pseudoLinkPdfUtil;

    public CheckOnClickPdfUtil(HasOnClickPdfUtil hasOnClickPdfUtil,
        NoOnclickPdfUtil noOnclickPdfUtil, PseudoLinkPdfUtil pseudoLinkPdfUtil) {
        this.hasOnClickPdfUtil = hasOnClickPdfUtil;
        this.noOnclickPdfUtil = noOnclickPdfUtil;
        this.pseudoLinkPdfUtil = pseudoLinkPdfUtil;
    }

    public String checkOnClickPdf(
        WebDriver webDriver, String pageUrl, PdfType pdfType, List<WebElement> pdfLinks,
        ChildPdfTagType childPdfTagType, int index) {

        if (pdfType.equals(PdfType.HAS_ONCLICK)) {

            String onClickPdfScript = pdfLinks.get(index).getAttribute("onclick");

            return hasOnClickPdfUtil.getPdfLinkByOnClick(pageUrl, onClickPdfScript);
        } else if (pdfType.equals(PdfType.NO_ONCLICK)) {
            return noOnclickPdfUtil.getPdfLink(webDriver, pdfLinks, childPdfTagType, index);
        } else if (pdfType.equals(PdfType.PSEUDO_LINK)) {

            WebElement pseudoLinkElement = pdfLinks.get(index);

            return pseudoLinkPdfUtil.getPdfLinkByPseudoLink(pageUrl, pseudoLinkElement);
        } else {
            throw new WebDriverException("Unsupported pdf type");
        }
    }
}
