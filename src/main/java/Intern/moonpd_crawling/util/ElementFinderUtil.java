package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.child.ChildBackTagType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentBackTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.lstCrawling.AnchorTagLstUtil;
import Intern.moonpd_crawling.util.pdfCrawling.AnchorTagPdfUtil;
import Intern.moonpd_crawling.util.pdfCrawling.ButtonTagPdfUtil;
import Intern.moonpd_crawling.util.pdfCrawling.ImgTagPdfUtil;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementFinderUtil {

    private final AnchorTagLstUtil anchorTagLstUtil;
    private final AnchorTagPdfUtil anchorTagPdfUtil;
    private final ButtonTagPdfUtil buttonTagPdfUtil;
    private final ImgTagPdfUtil imgTagPdfUtil;
    private final ElementExtendedUtil elementExtendedUtil;

    public ElementFinderUtil(AnchorTagLstUtil anchorTagLstUtil, AnchorTagPdfUtil anchorTagPdfUtil,
        ButtonTagPdfUtil buttonTagPdfUtil,
        ImgTagPdfUtil imgTagPdfUtil, ElementExtendedUtil elementExtendedUtil) {
        this.anchorTagLstUtil = anchorTagLstUtil;
        this.anchorTagPdfUtil = anchorTagPdfUtil;
        this.buttonTagPdfUtil = buttonTagPdfUtil;
        this.imgTagPdfUtil = imgTagPdfUtil;
        this.elementExtendedUtil = elementExtendedUtil;
    }

    public String getLstLink(List<WebElement> lstLinks, ChildLstTagType childLstTagType,
        int index) {
        if (childLstTagType.equals(ChildLstTagType.A)) {
            return anchorTagLstUtil.getLstLink(lstLinks, index);
        } else {
            throw new WebDriverException("Unsupported lst type");
        }
    }

    public String getPdfLink(WebDriver webDriver, List<WebElement> pdfLinks,
        ChildPdfTagType childPdfTagType, int index) {
        if (childPdfTagType.equals(ChildPdfTagType.A)) {
            return anchorTagPdfUtil.getPdfLink(pdfLinks, index);
        } else if (childPdfTagType.equals(ChildPdfTagType.BUTTON)) {
            return buttonTagPdfUtil.getPdfLink(pdfLinks, index);
        } else if (childPdfTagType.equals(ChildPdfTagType.IMG)) {
            return imgTagPdfUtil.getPdfLink(webDriver, pdfLinks, index);
        } else {
            throw new WebDriverException("Unsupported pdf type");
        }
    }

    public WebElement getBackElement(WebDriver webDriver,
        String parentBackIdentifier, ParentBackTagType parentBackTagType,
        String childBackIdentifier, ChildBackTagType childBackTagType, int backOrdinalNumber) {

        String cssSelector;

        if (backOrdinalNumber != 0) {
            if (!parentBackIdentifier.isEmpty() && !childBackIdentifier.isEmpty()) {
                cssSelector =
                    parentBackTagType + "." + parentBackIdentifier + " > " + childBackTagType + "."
                        + childBackIdentifier + ":nth-child(" + backOrdinalNumber + ")";
            } else if (!parentBackIdentifier.isEmpty()) {
                cssSelector =
                    parentBackTagType + "." + parentBackIdentifier + " > " + childBackTagType
                        + ":nth-child(" + backOrdinalNumber + ")";
            } else if (!childBackIdentifier.isEmpty()) {
                cssSelector =
                    parentBackTagType + " > " + childBackTagType + "." + childBackIdentifier
                        + ":nth-child(" + backOrdinalNumber + ")";
            } else {
                cssSelector =
                    parentBackTagType + " > " + childBackTagType
                        + ":nth-child(" + backOrdinalNumber + ")";
            }
        } else {
            cssSelector = childBackTagType + "." + childBackIdentifier;
        }

        return webDriver.findElement(By.cssSelector(cssSelector));
    }

    public List<WebElement> getLstElements(WebDriver webDriver, String parentLstIdentifier,
        ParentLstTagType parentLstTagType, String childLstIdentifier,
        ChildLstTagType childLstTagType,
        int lstOrdinalNumber) {

        String cssSelector;

        if (lstOrdinalNumber != 0) {
            if (!parentLstIdentifier.isEmpty() && !childLstIdentifier.isEmpty()) {
                cssSelector =
                    parentLstTagType + "." + parentLstIdentifier + " > " + childLstTagType + "."
                        + childLstIdentifier + ":nth-child(" + lstOrdinalNumber + ")";
            } else if (!parentLstIdentifier.isEmpty()) {
                cssSelector = parentLstTagType + "." + parentLstIdentifier + " > " + childLstTagType
                    + ":nth-child(" + lstOrdinalNumber + ")";
            } else if (!childLstIdentifier.isEmpty()) {
                cssSelector =
                    parentLstTagType + " > " + childLstTagType + "." + childLstIdentifier
                        + ":nth-child(" + lstOrdinalNumber + ")";
            } else {
                cssSelector =
                    parentLstTagType + " > " + childLstTagType
                        + ":nth-child(" + lstOrdinalNumber + ")";
            }
        } else {
            cssSelector = childLstTagType + "." + childLstIdentifier;
        }

        return webDriver.findElements(By.cssSelector(cssSelector));
    }

    public List<WebElement> getPdfElements(WebDriver webDriver,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        String parentPdfIdentifier, ParentPdfTagType parentPdfTagType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, int pdfOrdinalNumber) {

        String cssSelector;

        if (pdfOrdinalNumber != 0) {
            if (!parentPdfIdentifier.isEmpty() && !childPdfIdentifier.isEmpty()) {
                cssSelector =
                    parentPdfTagType + "." + parentPdfIdentifier + " > " + childPdfTagType + "."
                        + childPdfIdentifier + ":nth-child(" + pdfOrdinalNumber + ")";
            } else if (!parentPdfIdentifier.isEmpty()) {
                cssSelector = parentPdfTagType + "." + parentPdfIdentifier + " > " + childPdfTagType
                    + ":nth-child(" + pdfOrdinalNumber + ")";
            } else if (!childPdfIdentifier.isEmpty()) {
                cssSelector =
                    parentPdfTagType + " > " + childPdfTagType + "." + childPdfIdentifier
                        + ":nth-child(" + pdfOrdinalNumber + ")";
            } else {
                cssSelector =
                    parentPdfTagType + " > " + childPdfTagType
                        + ":nth-child(" + pdfOrdinalNumber + ")";
            }
        } else {
            cssSelector = childPdfTagType + "." + childPdfIdentifier;
        }

        if (extendedPdfType.equals(ExtendedPdfType.ON)) {
            cssSelector = elementExtendedUtil.getExtendedPdfElements(cssSelector,
                parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber);
        }

        return webDriver.findElements(By.cssSelector(cssSelector));
    }

    public List<WebElement> getTitleElements(WebDriver webDriver, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber) {

        String cssSelector;

        if (titleOrdinalNumber != 0) {
            if (!parentTitleIdentifier.isEmpty() && !childTitleIdentifier.isEmpty()) {
                cssSelector =
                    parentTitleTagType + "." + parentTitleIdentifier + " > " + childTitleTagType
                        + "."
                        + childTitleIdentifier + ":nth-child(" + titleOrdinalNumber + ")";
            } else if (!parentTitleIdentifier.isEmpty()) {
                cssSelector =
                    parentTitleTagType + "." + parentTitleIdentifier + " > " + childTitleTagType
                        + ":nth-child(" + titleOrdinalNumber + ")";
            } else if (!childTitleIdentifier.isEmpty()) {
                cssSelector =
                    parentTitleTagType + " > " + childTitleTagType + "." + childTitleIdentifier
                        + ":nth-child(" + titleOrdinalNumber + ")";
            } else {
                cssSelector =
                    parentTitleTagType + " > " + childTitleTagType
                        + ":nth-child(" + titleOrdinalNumber + ")";
            }
        } else {
            cssSelector = childTitleTagType + "." + childTitleIdentifier;
        }

        return webDriver.findElements(By.cssSelector(cssSelector));
    }
}
