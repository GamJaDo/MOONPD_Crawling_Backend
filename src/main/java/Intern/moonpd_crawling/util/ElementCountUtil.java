package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildYearTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentYearTagType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class ElementCountUtil {

    public int getTotalPageCnt(WebDriver webDriver, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType) {

        String parentSelector = parentNextPageTagType + "." + parentNextPageIdentifier;
        String childSelector = childNextPageTagType + "." + childNextPageIdentifier;
        String fullSelector = parentSelector + " " + childSelector;

        return webDriver.findElements(By.cssSelector(fullSelector)).size();
    }

    public int getTotalYearCnt(WebDriver webDriver, String parentYearIdentifier,
        ParentYearTagType parentYearTagType, String childYearIdentifier,
        ChildYearTagType childYearTagType) {

        String parentSelector = parentYearTagType + "." + parentYearIdentifier;
        String childSelector = childYearTagType + "." + childYearIdentifier;
        String fullSelector = parentSelector + " " + childSelector;

        return webDriver.findElements(By.cssSelector(fullSelector)).size();
    }
}
