package Intern.moonpd_crawling.util.structure;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.util.CheckOnClickUtil;
import Intern.moonpd_crawling.util.ElementFinderUtil;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ListedContentStructureUtil {

    private final CheckOnClickUtil checkOnClickUtil;
    private final ElementFinderUtil elementFinderUtil;

    public ListedContentStructureUtil(CheckOnClickUtil checkOnClickUtil,
        ElementFinderUtil elementFinderUtil) {
        this.checkOnClickUtil = checkOnClickUtil;
        this.elementFinderUtil = elementFinderUtil;
    }

    public void crawl(WebDriver webDriver, String pageUrl, Target target,
        LstType lstType,
        String parentLstIdentifier, ParentLstTagType parentLstTagType,
        String childLstIdentifier, ChildLstTagType childLstTagType, int lstOrdinalNumber,
        int totalPage,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType,
        int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        NextPageType nextPageType, String nextIdentifier) {

        List<WebElement> lstLinks = null;
        List<WebElement> titles = null;

        try {
            for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
                Thread.sleep(500);

                lstLinks = elementFinderUtil.getLstElements(webDriver,
                    parentLstIdentifier, parentLstTagType, childLstIdentifier, childLstTagType,
                    lstOrdinalNumber);
                if (lstLinks.isEmpty()) {
                    throw new WebDriverException(
                        "No lst links found for identifier: " + parentLstIdentifier + " or "
                            + childLstIdentifier);
                }

                titles = elementFinderUtil.getTitleElements(webDriver, parentTitleIdentifier,
                    parentTitleTagType, childTitleIdentifier, childTitleTagType,
                    titleOrdinalNumber);
                if (titles.isEmpty()) {
                    throw new WebDriverException(
                        "No titles found for identifier: " + parentTitleIdentifier + " or "
                            + childTitleIdentifier);
                }

                if (lstLinks.size() != titles.size()) {
                    int diff = Math.abs(lstLinks.size() - titles.size());

                    if (lstLinks.size() > titles.size()) {
                        lstLinks = lstLinks.subList(diff, lstLinks.size());
                    } else {
                        titles = titles.subList(diff, titles.size());
                    }
                }

                for (int i = 0; i < lstLinks.size(); i++) {
                    /*
                    lstLinks = elementFinderUtil.getLstElements(webDriver,
                        parentLstIdentifier, parentLstTagType,
                        childLstIdentifier, childLstTagType, lstOrdinalNumber);

                    stLinks 리스트는 페이지가 업데이트되거나 DOM이 변경될 때 "stale" 상태가 되어 이전에 저장된 요소 참조가 더 이상 유효하지 않게되므로
                    새로 조회(re-fetch) 함...

                    */
                    String titleText = titles.get(i).getText();

                    checkOnClickUtil.checkOnClickLst(pageUrl, target,
                        extendedPdfType, parentExtendedPdfIdentifier, parentExtendedPdfTagType,
                        extendedPdfOrdinalNumber, lstLinks, lstType, childLstTagType, pdfType, parentPdfIdentifier,
                        parentPdfTagType, childPdfIdentifier, childPdfTagType, pdfOrdinalNumber,
                        titleText, i);
                }

                if (currentPage < totalPage) {
                    checkOnClickUtil.checkOnClickNextPage(webDriver, nextPageType, nextIdentifier,
                        currentPage);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException("Crawling interrupted");
        }
    }
}
