package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.exception.WebDriverException;
import Intern.moonpd_crawling.status.type.LinkType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ElementLinkExtractorUtil {

    private final ElementCountUtil elementCountUtil;
    private final NextPageLinkExtenderUtil nextPageLinkExtenderUtil;

    public ElementLinkExtractorUtil(ElementCountUtil elementCountUtil,
        NextPageLinkExtenderUtil nextPageLinkExtenderUtil) {
        this.elementCountUtil = elementCountUtil;
        this.nextPageLinkExtenderUtil = nextPageLinkExtenderUtil;
    }

    public List<Map<String, Integer>> getYearLinks(String pageUrl, LinkType yearType,
        List<WebElement> yearElements) {

        List<Map<String, Integer>> yearPageLinks = new ArrayList<>();

        if (yearType.equals(LinkType.OPTION_LINK)) {
            int startYear = 0;

            for (WebElement element : yearElements) {
                String rawYear = element.getAttribute("value");

                if (rawYear != null && rawYear.matches("\\d{4}")) {
                    startYear = Integer.parseInt(rawYear);
                    break;
                }
            }

            if (startYear == 0) {
                return yearPageLinks;
            }

            for (int i = 0; i < yearElements.size(); i++) {
                Map<String, Integer> map = new HashMap<>();
                map.put(pageUrl.replace(String.valueOf(startYear), String.valueOf(startYear - i)),
                    startYear - i);
                yearPageLinks.add(map);
            }

            return yearPageLinks;
        } else if (yearType.equals(LinkType.ONCLICK_LINK)) {

            for (WebElement yearElement : yearElements) {
                String text = yearElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(yearElement.getAttribute("onclick"), pageNumber);
                    yearPageLinks.add(map);
                }
            }

            yearPageLinks = elementCountUtil.checkFirstNextPageLink(yearPageLinks);

            return yearPageLinks;
        } else if (yearType.equals(LinkType.HREF_LINK) || yearType.equals(LinkType.JAVASCRIPT_LINK)) {

            for (WebElement yearElement : yearElements) {
                String text = yearElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(yearElement.getAttribute("href"), pageNumber);
                    yearPageLinks.add(map);
                }
            }

            yearPageLinks = elementCountUtil.checkFirstNextPageLink(yearPageLinks);

            return yearPageLinks;
        } else {
            throw new WebDriverException("Unsupported year type");
        }
    }

    public List<Map<String, Integer>> getNextPageLinks(int totalPage, LinkType nextPageType,
        List<WebElement> nextPageElements) {

        List<Map<String, Integer>> nextPageLinks = new ArrayList<>();

        if (nextPageType.equals(LinkType.ONCLICK_LINK)) {

            for (WebElement nextPageElement : nextPageElements) {
                String text = nextPageElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(nextPageElement.getAttribute("onclick"), pageNumber);
                    nextPageLinks.add(map);
                }
            }

            nextPageLinks = elementCountUtil.checkFirstNextPageLink(nextPageLinks);
            nextPageLinks = nextPageLinkExtenderUtil.extendedNextPageLinks(totalPage, nextPageLinks);

            return nextPageLinks;
        } else if (nextPageType.equals(LinkType.HREF_LINK) || nextPageType.equals(LinkType.JAVASCRIPT_LINK)) {

            for (WebElement nextPageElement : nextPageElements) {
                String text = nextPageElement.getText().replaceAll("\"", "").trim();

                if (text.matches("^\\d+$")) {
                    int pageNumber = Integer.parseInt(text);
                    Map<String, Integer> map = new HashMap<>();
                    map.put(nextPageElement.getAttribute("href"), pageNumber);
                    nextPageLinks.add(map);
                }
            }

            nextPageLinks = elementCountUtil.checkFirstNextPageLink(nextPageLinks);
            nextPageLinks = nextPageLinkExtenderUtil.extendedNextPageLinks(totalPage, nextPageLinks);

            return nextPageLinks;
        } else if (nextPageType.equals(LinkType.NONE)) {

            return nextPageLinks;
        } else {
            throw new WebDriverException("Unsupported nextPage type");
        }
    }
}
