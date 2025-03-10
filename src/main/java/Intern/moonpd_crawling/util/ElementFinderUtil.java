package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.TitleType;
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
    private final GapAwarePdfExtractorUtil gapAwarePdfExtractorUtil;

    public ElementFinderUtil(ElementExtendedUtil elementExtendedUtil, ElementFilterUtil elementFilterUtil,
        GapAwarePdfExtractorUtil gapAwarePdfExtractorUtil) {
        this.elementExtendedUtil = elementExtendedUtil;
        this.elementFilterUtil = elementFilterUtil;
        this.gapAwarePdfExtractorUtil = gapAwarePdfExtractorUtil;
    }

    public List<WebElement> getLstElements(WebDriver webDriver, ExtendedType extendedLstType,
        String extendedLstIdentifier, TagType extendedLstTagType, SelectorType extendedLstSelectorType,
        LinkType lstType, String parentLstIdentifier, TagType parentLstTagType,
        SelectorType parentLstSelectorType, String childLstIdentifier, TagType childLstTagType,
        SelectorType childLstSelectorType, int lstOrdinalNumber) {

        String cssSelector = getCssSelector(parentLstIdentifier, parentLstTagType, parentLstSelectorType,
            childLstIdentifier, childLstTagType, childLstSelectorType, lstOrdinalNumber);

        List<WebElement> lstElements = webDriver.findElements(By.cssSelector(cssSelector));
        if (extendedLstType.equals(ExtendedType.ON)) {
            lstElements = elementExtendedUtil.getExtendedElements(extendedLstIdentifier,
                extendedLstTagType, extendedLstSelectorType, lstElements);
        }

        return elementFilterUtil.getLstElementWithLink(lstType, lstElements);
    }

    public List<WebElement> getYearElements(WebDriver webDriver, LinkType yearType,
        String parentYearIdentifier, TagType parentYearTagType, SelectorType parentYearSelectorType,
        String childYearIdentifier, TagType childYearTagType, SelectorType childYearSelectorType,
        int yearOrdinalNumber) {

        String cssSelector = getCssSelector(parentYearIdentifier, parentYearTagType, parentYearSelectorType,
            childYearIdentifier, childYearTagType, childYearSelectorType, yearOrdinalNumber);

        WebElement parentElement = webDriver.findElement(By.cssSelector(cssSelector));
        List<WebElement> result = null;
        if (yearType.equals(LinkType.OPTION_LINK)) {
            result = parentElement.findElements(By.tagName("option"));
        } else {
            result = parentElement.findElements(By.tagName("a"));
            if (result.isEmpty()) {
                result = parentElement.findElements(By.tagName("button"));
            }
            if (result.isEmpty()) {
                throw new WebDriverException("No matching tag");
            }
        }

        return result;
    }

    public List<WebElement> getPdfElements(WebDriver webDriver, LinkType lstType,
        ExtendedType extendedPdfType, String extendedPdfIdentifier, TagType extendedPdfTagType,
        SelectorType extendedPdfSelectorType, LinkType pdfType, String parentPdfIdentifier,
        TagType parentPdfTagType, SelectorType parentPdfSelectorType, String childPdfIdentifier,
        TagType childPdfTagType, SelectorType childPdfSelectorType, int pdfOrdinalNumber, TitleType titleType,
        int titleCnt) {

        List<WebElement> pdfElements = null;

        String cssSelector = getCssSelector(parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
            childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber);

        if (titleCnt != 0) {

            String extendedCssSelector = getCssSelector("", TagType.NONE, SelectorType.NONE,
                extendedPdfIdentifier, extendedPdfTagType, extendedPdfSelectorType, 0);
            String parentCssSelector = getCssSelector("", TagType.NONE, SelectorType.NONE,
                parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType, 0);

            pdfElements = gapAwarePdfExtractorUtil.getPdfElementsFromBlogWrap(webDriver, extendedCssSelector,
                parentCssSelector, cssSelector);

            return pdfElements;
        } else {
            pdfElements = webDriver.findElements(By.cssSelector(cssSelector));

            if (extendedPdfType.equals(ExtendedType.ON)) {
                pdfElements = elementExtendedUtil.getExtendedElements(extendedPdfIdentifier,
                    extendedPdfTagType, extendedPdfSelectorType, pdfElements);
            }
            if (!lstType.equals(LinkType.NONE) && !titleType.equals(TitleType.OUT)) {
                pdfElements = elementExtendedUtil.getDescendantPdfTextElements(pdfElements);
            }

            return elementFilterUtil.getPdfElementWithLink(pdfType, pdfElements);
        }
    }

    public List<WebElement> getTitleElements(WebDriver webDriver, LinkType lstType,
        ExtendedType extendedTitleType, String extendedTitleIdentifier, TagType extendedTitleTagType,
        SelectorType extendedTitleSelectorType, TitleType titleType, String parentTitleIdentifier,
        TagType parentTitleTagType, SelectorType parentTitleSelectorType, String childTitleIdentifier,
        TagType childTitleTagType, SelectorType childTitleSelectorType, int titleOrdinalNumber) {

        String cssSelector = getCssSelector(parentTitleIdentifier, parentTitleTagType,
            parentTitleSelectorType, childTitleIdentifier, childTitleTagType, childTitleSelectorType,
            titleOrdinalNumber);

        List<WebElement> titleElements = webDriver.findElements(By.cssSelector(cssSelector));
        if (extendedTitleType.equals(ExtendedType.ON)) {
            titleElements = elementExtendedUtil.getExtendedElements(extendedTitleIdentifier,
                extendedTitleTagType, extendedTitleSelectorType, titleElements);
        }
        if (!lstType.equals(LinkType.NONE) && !titleType.equals(TitleType.OUT)) {
            titleElements = elementExtendedUtil.getDescendantPdfTextElements(titleElements);
        }

        return titleElements;
    }

    public List<WebElement> getNextPageElements(WebDriver webDriver, LinkType nextPageType,
        String parentNextPageIdentifier, TagType parentNextPageTagType,
        SelectorType parentNextPageSelectorType, String childNextPageIdentifier, TagType childNextPageTagType,
        SelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {

        if (!nextPageType.equals(LinkType.NONE)) {

            String cssSelector = getCssSelector(parentNextPageIdentifier, parentNextPageTagType,
                parentNextPageSelectorType, childNextPageIdentifier, childNextPageTagType,
                childNextPageSelectorType, nextPageOrdinalNumber);

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

    private String getCssSelector(String parentIdentifier, TagType parentTagType,
        SelectorType parentSelectorType, String childIdentifier, TagType childTagType,
        SelectorType childSelectorType, int ordinalNumber) {

        String parentSelector;
        if (!parentIdentifier.isEmpty()) {
            if (parentSelectorType == SelectorType.CLASS) {
                parentSelector = parentTagType + "." + parentIdentifier;
            } else if (parentSelectorType == SelectorType.STYLE) {
                parentSelector = parentTagType + "[style*=\"" + parentIdentifier + "\"]";
            } else if (parentSelectorType == SelectorType.ID) {
                parentSelector = parentTagType + "#" + parentIdentifier;
            } else {
                parentSelector = parentTagType.toString();
            }
        } else {
            parentSelector = parentTagType.toString();
        }

        String childSelector;
        if (!childIdentifier.isEmpty()) {
            if (childSelectorType == SelectorType.CLASS) {
                childSelector = childTagType + "." + childIdentifier;
            } else if (childSelectorType == SelectorType.STYLE) {
                childSelector = childTagType + "[style*=\"" + childIdentifier + "\"]";
            } else if (childSelectorType == SelectorType.ID) {
                childSelector = childTagType + "#" + childIdentifier;
            } else {
                childSelector = childTagType.toString();
            }
        } else {
            childSelector = childTagType.toString();
        }

        String cssSelector;
        if (ordinalNumber != 0) {
            cssSelector = parentSelector + " > " + childSelector + ":nth-child(" + ordinalNumber + ")";
        } else {
            cssSelector = childSelector;
        }

        return cssSelector;
    }
}
