package Intern.moonpd_crawling.util.pdfCrawling;

import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Type3PdfUtil {

    public String getPdfLink(WebDriver driver, List<WebElement> pdfLinks, ParentPdfTagType parentPdfTagType, String pdfOnClick, int index) {
        // 페이지 인덱스 업데이트
        String updatedOnclickString = updatePageInString(pdfOnClick, index);

        for (WebElement pdfLink : pdfLinks) {
            List<WebElement> aTags = pdfLink.findElements(By.tagName(parentPdfTagType.toString().toLowerCase()));

            System.out.println("Processing pdfTagType: " + parentPdfTagType.toString().toLowerCase());
            System.out.println("Found aTags count: " + aTags.size());

            for (WebElement aTag : aTags) {
                String onclick = aTag.getAttribute("onclick");
                System.out.println("Found onclick attribute: " + onclick);

                if (onclick != null && !onclick.isEmpty() && onclick.equals(updatedOnclickString)) {
                    System.out.println("Executing JavaScript: " + onclick);

                    try {
                        // JavaScript 실행
                        String script = "return " + onclick + ";";
                        Object result = ((JavascriptExecutor) driver).executeScript(script);

                        if (result instanceof String) {
                            return (String) result;
                        } else {
                            throw new NotFoundException("JavaScript execution did not return a valid URL for onclick: " + onclick);
                        }
                    } catch (Exception e) {
                        System.err.println("Error executing JavaScript for onclick: " + onclick);
                        throw new NotFoundException("Failed to execute JavaScript for onclick: " + onclick, e);
                    }
                }
            }
        }

        throw new NotFoundException("No matching <a> tag found with onclick: " + updatedOnclickString);
    }


    private String updatePageInString(String value, int currentPage) {
        int numberIndexStart = -1;
        int numberIndexEnd = -1;

        for (int i = value.length() - 1; i >= 0; i--) {
            if (Character.isDigit(value.charAt(i))) {
                if (numberIndexEnd == -1) {
                    numberIndexEnd = i;
                }
                numberIndexStart = i;
            } else if (numberIndexEnd != -1) {
                break;
            }
        }

        if (numberIndexStart == -1 || numberIndexEnd == -1) {
            throw new NotFoundException("No number found in the nextIdentifier string: " + value);
        }

        String prefix = value.substring(0, numberIndexStart);
        String suffix = value.substring(numberIndexEnd + 1);

        String numberString = value.substring(numberIndexStart, numberIndexEnd + 1);
        int number = Integer.parseInt(numberString);

        return prefix + (number - currentPage) + suffix;
    }
}
