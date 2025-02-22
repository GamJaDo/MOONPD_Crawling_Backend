package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.selector.child.ChildLstSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.child.ChildYearTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentYearTagType;
import Intern.moonpd_crawling.util.pdfCrawling.AnchorTagPdfUtil;
import Intern.moonpd_crawling.util.pdfCrawling.ButtonTagPdfUtil;
import Intern.moonpd_crawling.util.pdfCrawling.ImgTagPdfUtil;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementFinderUtil {

    private final AnchorTagPdfUtil anchorTagPdfUtil;
    private final ButtonTagPdfUtil buttonTagPdfUtil;
    private final ImgTagPdfUtil imgTagPdfUtil;
    private final ElementExtendedUtil elementExtendedUtil;
    private final ElementFilterUtil elementFilterUtil;

    public ElementFinderUtil(AnchorTagPdfUtil anchorTagPdfUtil,
        ButtonTagPdfUtil buttonTagPdfUtil, ImgTagPdfUtil imgTagPdfUtil,
        ElementExtendedUtil elementExtendedUtil, ElementFilterUtil elementFilterUtil) {
        this.anchorTagPdfUtil = anchorTagPdfUtil;
        this.buttonTagPdfUtil = buttonTagPdfUtil;
        this.imgTagPdfUtil = imgTagPdfUtil;
        this.elementExtendedUtil = elementExtendedUtil;
        this.elementFilterUtil = elementFilterUtil;
    }

    public String getPdfLink(WebDriver webDriver, List<WebElement> pdfLinks,
        ChildPdfTagType childPdfTagType, int index) {
        if (childPdfTagType.equals(ChildPdfTagType.A)) {
            return anchorTagPdfUtil.getPdfLink(pdfLinks, index);
        } else if (childPdfTagType.equals(ChildPdfTagType.BUTTON)) {
            return buttonTagPdfUtil.getPdfLink(pdfLinks, index);
        } else if (childPdfTagType.equals(ChildPdfTagType.IMG)) {
            return "";
        } else {
            throw new WebDriverException("Unsupported pdf type");
        }
    }

    public List<WebElement> getLstElements(WebDriver webDriver, String parentLstIdentifier,
        ParentLstTagType parentLstTagType, ParentLstSelectorType parentLstSelectorType,
        String childLstIdentifier, ChildLstTagType childLstTagType,
        ChildLstSelectorType childLstSelectorType, int lstOrdinalNumber) {

        String parentSelector;
        if (!parentLstIdentifier.isEmpty()) {
            if (parentLstSelectorType == ParentLstSelectorType.CLASS) {
                parentSelector = parentLstTagType + "." + parentLstIdentifier;
            } else if (parentLstSelectorType == ParentLstSelectorType.STYLE) {
                parentSelector = parentLstTagType + "[style*='" + parentLstIdentifier + "']";
            } else {
                parentSelector = parentLstTagType.toString();
            }
        } else {
            parentSelector = parentLstTagType.toString();
        }

        String childSelector;
        if (!childLstIdentifier.isEmpty()) {
            if (childLstSelectorType == ChildLstSelectorType.CLASS) {
                childSelector = childLstTagType + "." + childLstIdentifier;
            } else if (childLstSelectorType == ChildLstSelectorType.STYLE) {
                childSelector = childLstTagType + "[style*='" + childLstIdentifier + "']";
            } else {
                childSelector = childLstTagType.toString();
            }
        } else {
            childSelector = childLstTagType.toString();
        }

        String cssSelector;
        if (lstOrdinalNumber != 0) {
            cssSelector = parentSelector + " > " + childSelector + ":nth-child(" + lstOrdinalNumber + ")";
        } else {
            cssSelector = childSelector;
        }

        List<WebElement> lstElements = webDriver.findElements(By.cssSelector(cssSelector));
        return elementFilterUtil.getLstElementWithLink(lstElements);
    }

    public List<WebElement> getYearElements(WebDriver webDriver, String parentYearIdentifier,
        ParentYearTagType parentYearTagType, String childYearIdentifier,
        ChildYearTagType childYearTagType, int yearOrdinalNumber) {

        String cssSelector;

        if (yearOrdinalNumber != 0) {
            if (!parentYearIdentifier.isEmpty() && !childYearIdentifier.isEmpty()) {
                cssSelector =
                    parentYearTagType + "." + parentYearIdentifier + " > " + childYearTagType
                        + "."
                        + childYearIdentifier + ":nth-child(" + yearOrdinalNumber + ")";
            } else if (!parentYearIdentifier.isEmpty()) {
                cssSelector =
                    parentYearTagType + "." + parentYearIdentifier + " > " + childYearTagType
                        + ":nth-child(" + yearOrdinalNumber + ")";
            } else if (!childYearIdentifier.isEmpty()) {
                cssSelector =
                    parentYearTagType + " > " + childYearTagType + "." + childYearIdentifier
                        + ":nth-child(" + yearOrdinalNumber + ")";
            } else {
                cssSelector =
                    parentYearTagType + " > " + childYearTagType
                        + ":nth-child(" + yearOrdinalNumber + ")";
            }
        } else {
            cssSelector = childYearTagType + "." + childYearIdentifier;
        }

        WebElement parentElement = webDriver.findElement(By.cssSelector(cssSelector));
        return parentElement.findElements(By.tagName("a"));
    }

    public List<WebElement> getPdfElements(WebDriver webDriver,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType,
        int pdfOrdinalNumber) {

        String parentSelector;
        if (!parentPdfIdentifier.isEmpty()) {
            if (parentPdfSelectorType == ParentPdfSelectorType.CLASS) {
                parentSelector = parentPdfTagType + "." + parentPdfIdentifier;
            } else if (parentPdfSelectorType == ParentPdfSelectorType.STYLE) {
                parentSelector = parentPdfTagType + "[style*='" + parentPdfIdentifier + "']";
            } else {
                parentSelector = parentPdfTagType.toString();
            }
        } else {
            parentSelector = parentPdfTagType.toString();
        }

        String childSelector;
        if (!childPdfIdentifier.isEmpty()) {
            if (childPdfSelectorType == ChildPdfSelectorType.CLASS) {
                childSelector = childPdfTagType + "." + childPdfIdentifier;
            } else if (childPdfSelectorType == ChildPdfSelectorType.STYLE) {
                childSelector = childPdfTagType + "[style*='" + childPdfIdentifier + "']";
            } else {
                childSelector = childPdfTagType.toString();
            }
        } else {
            childSelector = childPdfTagType.toString();
        }

        String cssSelector;
        if (pdfOrdinalNumber != 0) {
            cssSelector = parentSelector + " > " + childSelector + ":nth-child(" + pdfOrdinalNumber + ")";
        } else {
            cssSelector = childSelector;
        }


        if (extendedPdfType.equals(ExtendedPdfType.ON)) {
            cssSelector = elementExtendedUtil.getExtendedPdfElements(cssSelector,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber);
        }

        List<WebElement> pdfElements = webDriver.findElements(By.cssSelector(cssSelector));
        return elementFilterUtil.getPdfElementWithLink(pdfElements);
    }

    public List<WebElement> getTitleElements(WebDriver webDriver, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, ParentTitleSelectorType parentTitleSelectorType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType,
        ChildTitleSelectorType childTitleSelectorType, int titleOrdinalNumber) {

        String parentSelector;
        if (!parentTitleIdentifier.isEmpty()) {
            if (parentTitleSelectorType == ParentTitleSelectorType.CLASS) {
                parentSelector = parentTitleTagType + "." + parentTitleIdentifier;
            } else if (parentTitleSelectorType == ParentTitleSelectorType.STYLE) {
                parentSelector = parentTitleTagType + "[style*='" + parentTitleIdentifier + "']";
            } else {
                parentSelector = parentTitleTagType.toString();
            }
        } else {
            parentSelector = parentTitleTagType.toString();
        }

        String childSelector;
        if (!childTitleIdentifier.isEmpty()) {
            if (childTitleSelectorType == ChildTitleSelectorType.CLASS) {
                childSelector = childTitleTagType + "." + childTitleIdentifier;
            } else if (childTitleSelectorType == ChildTitleSelectorType.STYLE) {
                childSelector = childTitleTagType + "[style*='" + childTitleIdentifier + "']";
            } else {
                childSelector = childTitleTagType.toString();
            }
        } else {
            childSelector = childTitleTagType.toString();
        }

        String cssSelector;
        if (titleOrdinalNumber != 0) {
            cssSelector = parentSelector + " > " + childSelector + ":nth-child(" + titleOrdinalNumber + ")";
        } else {
            cssSelector = childSelector;
        }

        return webDriver.findElements(By.cssSelector(cssSelector));
    }

    public List<WebElement> getNextPageElements(WebDriver webDriver, NextPageType nextPageType,
        String parentNextPageIdentifier, ParentNextPageTagType parentNextPageTagType,
        String childNextPageIdentifier, ChildNextPageTagType childNextPageTagType,
        int nextPageOrdinalNumber) {

        if (!nextPageType.equals(NextPageType.NONE)) {

            String cssSelector;

            if (nextPageOrdinalNumber != 0) {
                if (!parentNextPageIdentifier.isEmpty() && !childNextPageIdentifier.isEmpty()) {
                    cssSelector =
                        parentNextPageTagType + "." + parentNextPageIdentifier + " > "
                            + childNextPageTagType
                            + "."
                            + childNextPageIdentifier + ":nth-child(" + nextPageOrdinalNumber + ")";
                } else if (!parentNextPageIdentifier.isEmpty()) {
                    cssSelector =
                        parentNextPageTagType + "." + parentNextPageIdentifier + " > "
                            + childNextPageTagType
                            + ":nth-child(" + nextPageOrdinalNumber + ")";
                } else if (!childNextPageIdentifier.isEmpty()) {
                    cssSelector =
                        parentNextPageTagType + " > " + childNextPageTagType + "."
                            + childNextPageIdentifier
                            + ":nth-child(" + nextPageOrdinalNumber + ")";
                } else {
                    cssSelector =
                        parentNextPageTagType + " > " + childNextPageTagType
                            + ":nth-child(" + nextPageOrdinalNumber + ")";
                }
            } else {
                cssSelector = childNextPageTagType + "." + childNextPageIdentifier;
            }

            WebElement parentElement = webDriver.findElement(By.cssSelector(cssSelector));
            return parentElement.findElements(By.tagName("a"));
        } else {
            return new ArrayList<>();
        }
    }
}
