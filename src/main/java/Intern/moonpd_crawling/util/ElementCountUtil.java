package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildYearTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentYearTagType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementCountUtil {

    private final ElementFinderUtil elementFinderUtil;

    public ElementCountUtil(ElementFinderUtil elementFinderUtil) {
        this.elementFinderUtil = elementFinderUtil;
    }

    public int getTotalYearCnt(WebDriver webDriver, String parentYearIdentifier,
        ParentYearTagType parentYearTagType, String childYearIdentifier,
        ChildYearTagType childYearTagType, int yearOrdinalNumber) {

        return elementFinderUtil.getYearElements(webDriver, parentYearIdentifier, parentYearTagType,
            childYearIdentifier, childYearTagType, yearOrdinalNumber).size();
    }

    public int getTotalPageCnt(WebDriver webDriver, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType, int nextPageOrdinalNumber) {

        int totalNextPageCount = elementFinderUtil.getNextPageElements(webDriver, parentNextPageIdentifier,
            parentNextPageTagType, childNextPageIdentifier, childNextPageTagType,
            nextPageOrdinalNumber).size();

        if (totalNextPageCount == 0){
            return 1;
        }
        return totalNextPageCount;
    }
}
