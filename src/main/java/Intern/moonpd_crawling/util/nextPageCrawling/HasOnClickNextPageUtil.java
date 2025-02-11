package Intern.moonpd_crawling.util.nextPageCrawling;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HasOnClickNextPageUtil {

    public void goToNextPageByOnclick(WebDriver webDriver, String nextIdentifier, int currentPage) {
        String updatedOnclickScript = updatePageInString(nextIdentifier, currentPage);
        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(updatedOnclickScript);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute onclick script: " + updatedOnclickScript,
                e);
        }
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

        return prefix + currentPage + suffix;
    }
}
