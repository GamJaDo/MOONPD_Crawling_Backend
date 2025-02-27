package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.selector.child.ChildLstSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedLstSelectorType;
import Intern.moonpd_crawling.status.selector.extended.ExtendedPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedLstTagType;
import Intern.moonpd_crawling.status.type.ExtendedLstType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.child.ChildYearTagType;
import Intern.moonpd_crawling.status.tag.extended.ExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentYearTagType;
import Intern.moonpd_crawling.status.type.PdfType;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementFinderUtil {

    private final ElementExtendedUtil elementExtendedUtil;
    private final ElementFilterUtil elementFilterUtil;

    public ElementFinderUtil(ElementExtendedUtil elementExtendedUtil,
        ElementFilterUtil elementFilterUtil) {
        this.elementExtendedUtil = elementExtendedUtil;
        this.elementFilterUtil = elementFilterUtil;
    }

    public List<WebElement> getLstElements(WebDriver webDriver, ExtendedLstType extendedLstType,
        String extendedLstIdentifier, ExtendedLstTagType extendedLstTagType,
        ExtendedLstSelectorType extendedLstSelectorType, LstType lstType, String parentLstIdentifier,
        ParentLstTagType parentLstTagType, ParentLstSelectorType parentLstSelectorType,
        String childLstIdentifier, ChildLstTagType childLstTagType, ChildLstSelectorType childLstSelectorType,
        int lstOrdinalNumber) {

        String parentSelector;
        if (!parentLstIdentifier.isEmpty()) {
            if (parentLstSelectorType == ParentLstSelectorType.CLASS) {
                parentSelector = parentLstTagType + "." + parentLstIdentifier;
            } else if (parentLstSelectorType == ParentLstSelectorType.STYLE) {
                parentSelector = parentLstTagType + "[style*=\"" + parentLstIdentifier + "\"]";
            } else if (parentLstSelectorType == ParentLstSelectorType.ID) {
                parentSelector = parentLstTagType + "#" + parentLstIdentifier;
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
                childSelector = childLstTagType + "[style*=\"" + childLstIdentifier + "\"]";
            } else if (childLstSelectorType == ChildLstSelectorType.ID) {
                childSelector = childLstTagType + "#" + childLstIdentifier;
            } else {
                childSelector = childLstTagType.toString();
            }
        } else {
            childSelector = childLstTagType.toString();
        }

        String cssSelector;
        if (lstOrdinalNumber != 0) {
            cssSelector =
                parentSelector + " > " + childSelector + ":nth-child(" + lstOrdinalNumber + ")";
        } else {
            cssSelector = childSelector;
        }

        List<WebElement> lstElements = webDriver.findElements(By.cssSelector(cssSelector));
        if (extendedLstType.equals(ExtendedLstType.ON)) {
            lstElements = elementExtendedUtil.getExtendedLstElements(extendedLstIdentifier,
                extendedLstTagType, extendedLstSelectorType, lstElements);
        }

        return elementFilterUtil.getLstElementWithLink(lstType, lstElements);
    }

    public List<WebElement> getYearElements(WebDriver webDriver, String parentYearIdentifier,
        ParentYearTagType parentYearTagType, String childYearIdentifier, ChildYearTagType childYearTagType,
        int yearOrdinalNumber) {

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

        List<WebElement> result = parentElement.findElements(By.tagName("a"));
        if (result.isEmpty()) {
            result = parentElement.findElements(By.tagName("button"));
        }
        if (result.isEmpty()) {
            throw new WebDriverException("No matching tag");
        }

        return result;
    }

    public List<WebElement> getPdfElements(WebDriver webDriver, LstType lstType,
        ExtendedPdfType extendedPdfType, String extendedPdfIdentifier, ExtendedPdfTagType extendedPdfTagType,
        ExtendedPdfSelectorType extendedPdfSelectorType, PdfType pdfType, String parentPdfIdentifier,
        ParentPdfTagType parentPdfTagType, ParentPdfSelectorType parentPdfSelectorType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType,
        int pdfOrdinalNumber) {

        String parentSelector;
        if (!parentPdfIdentifier.isEmpty()) {
            if (parentPdfSelectorType == ParentPdfSelectorType.CLASS) {
                parentSelector = parentPdfTagType + "." + parentPdfIdentifier;
            } else if (parentPdfSelectorType == ParentPdfSelectorType.STYLE) {
                parentSelector = parentPdfTagType + "[style*=\"" + parentPdfIdentifier + "\"]";
            } else if (parentPdfSelectorType == ParentPdfSelectorType.ID) {
                parentSelector = parentPdfTagType + "#" + parentPdfIdentifier;
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
                childSelector = childPdfTagType + "[style*=\"" + childPdfIdentifier + "\"]";
            } else if (childPdfSelectorType == ChildPdfSelectorType.ID) {
                childSelector = childPdfTagType + "#" + childPdfIdentifier;
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

        List<WebElement> pdfElements = webDriver.findElements(By.cssSelector(cssSelector));
        if (extendedPdfType.equals(ExtendedPdfType.ON)) {
            pdfElements = elementExtendedUtil.getExtendedPdfElements(extendedPdfIdentifier,
                extendedPdfTagType, extendedPdfSelectorType, pdfElements);
        }
        if (!lstType.equals(LstType.NONE)) {
            pdfElements = elementExtendedUtil.getDescendantPdfTextElements(pdfElements);
        }

        return elementFilterUtil.getPdfElementWithLink(pdfType, pdfElements);
    }

    public List<WebElement> getTitleElements(WebDriver webDriver, LstType lstType,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        ParentTitleSelectorType parentTitleSelectorType, String childTitleIdentifier,
        ChildTitleTagType childTitleTagType, ChildTitleSelectorType childTitleSelectorType,
        int titleOrdinalNumber) {

        String parentSelector;
        if (!parentTitleIdentifier.isEmpty()) {
            if (parentTitleSelectorType == ParentTitleSelectorType.CLASS) {
                parentSelector = parentTitleTagType + "." + parentTitleIdentifier;
            } else if (parentTitleSelectorType == ParentTitleSelectorType.STYLE) {
                parentSelector = parentTitleTagType + "[style*=\"" + parentTitleIdentifier + "\"]";
            } else if (parentTitleSelectorType == ParentTitleSelectorType.ID) {
                parentSelector = parentTitleTagType + "#" + parentTitleIdentifier;
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
                childSelector = childTitleTagType + "[style*=\"" + childTitleIdentifier + "\"]";
            } else if (childTitleSelectorType == ChildTitleSelectorType.ID) {
                childSelector = childTitleTagType + "#" + childTitleIdentifier;
            } else {
                childSelector = childTitleTagType.toString();
            }
        } else {
            childSelector = childTitleTagType.toString();
        }

        String cssSelector;
        if (titleOrdinalNumber != 0) {
            cssSelector =
                parentSelector + " > " + childSelector + ":nth-child(" + titleOrdinalNumber + ")";
        } else {
            cssSelector = childSelector;
        }

        List<WebElement> titleElements = webDriver.findElements(By.cssSelector(cssSelector));
        if (!lstType.equals(LstType.NONE)) {
            titleElements = elementExtendedUtil.getDescendantPdfTextElements(titleElements);
        }


        return titleElements;
    }

    public List<WebElement> getNextPageElements(WebDriver webDriver, NextPageType nextPageType,
        String parentNextPageIdentifier, ParentNextPageTagType parentNextPageTagType,
        ParentNextPageSelectorType parentNextPageSelectorType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType,
        ChildNextPageSelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {

        if (!nextPageType.equals(NextPageType.NONE)) {

            String parentSelector;
            if (!parentNextPageIdentifier.isEmpty()) {
                if (parentNextPageSelectorType == ParentNextPageSelectorType.CLASS) {
                    parentSelector = parentNextPageTagType + "." + parentNextPageIdentifier;
                } else if (parentNextPageSelectorType == ParentNextPageSelectorType.STYLE) {
                    parentSelector =
                        parentNextPageTagType + "[style*=\"" + parentNextPageIdentifier + "\"]";
                } else if (parentNextPageSelectorType == ParentNextPageSelectorType.ID) {
                    parentSelector = parentNextPageTagType + "#" + parentNextPageIdentifier;
                } else {
                    parentSelector = parentNextPageTagType.toString();
                }
            } else {
                parentSelector = parentNextPageTagType.toString();
            }

            String childSelector;
            if (!childNextPageIdentifier.isEmpty()) {
                if (childNextPageSelectorType == ChildNextPageSelectorType.CLASS) {
                    childSelector = childNextPageTagType + "." + childNextPageIdentifier;
                } else if (childNextPageSelectorType == ChildNextPageSelectorType.STYLE) {
                    childSelector =
                        childNextPageTagType + "[style*=\"" + childNextPageIdentifier + "\"";
                } else if (childNextPageSelectorType == ChildNextPageSelectorType.ID) {
                    childSelector = childNextPageTagType + "#" + childNextPageIdentifier;
                } else {
                    childSelector = childNextPageTagType.toString();
                }
            } else {
                childSelector = childNextPageTagType.toString();
            }

            String cssSelector;
            if (nextPageOrdinalNumber != 0) {
                cssSelector =
                    parentSelector + " > " + childSelector + ":nth-child(" + nextPageOrdinalNumber
                        + ")";
            } else {
                cssSelector = childSelector;
            }

            WebElement parentElement = webDriver.findElement(By.cssSelector(cssSelector));

            List<WebElement> result = parentElement.findElements(By.tagName("a"));
            if (result.isEmpty()) {
                result = parentElement.findElements(By.tagName("button"));
            }
            if (result.isEmpty()) {
                throw new WebDriverException("No matching tag");
            }

            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
