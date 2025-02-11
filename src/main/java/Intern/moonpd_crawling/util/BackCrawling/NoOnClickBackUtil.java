package Intern.moonpd_crawling.util.BackCrawling;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class NoOnClickBackUtil {

    public void goToBackByElement(WebElement backElement) {

        backElement.click();
    }
}
