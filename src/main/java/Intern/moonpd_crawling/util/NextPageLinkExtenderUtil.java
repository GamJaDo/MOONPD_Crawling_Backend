package Intern.moonpd_crawling.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class NextPageLinkExtenderUtil {

    public List<Map<String, Integer>> extendedNextPageLinks(int totalPage,
        List<Map<String, Integer>> nextPageLinks) {

        String[] keywords = {
            "p=", "pg=", "pageIndex=", "page=", "curPage=", "pno=", "page_no=", "pgno=",
            "CPAGE=", "goPage(", "noticeList(", "Page2(", "search(", "linkPage(", "pageMove(",
            "Page_func(", "Pag(", "movePage(", "Page(", "pageNo=", "Page="
        };
        String pageWord = null;

        Map<String, Integer> lastMap = nextPageLinks.get(nextPageLinks.size() - 1);
        String lastNextPageLink = lastMap.entrySet().iterator().next().getKey();
        int lastNextPage = lastMap.entrySet().iterator().next().getValue();

        for (String keyword : keywords) {
            if (lastNextPageLink.contains(keyword)) {
                pageWord = keyword;
                break;
            }
        }

        if (pageWord == null || pageWord.isEmpty()) {
            throw new NullPointerException("pageWord is null or empty");
        }

        int keywordIndex = lastNextPageLink.indexOf(pageWord);
        String part0 = lastNextPageLink.substring(0, keywordIndex + pageWord.length());
        String part1 = lastNextPageLink.substring(keywordIndex + pageWord.length());

        String lastPageStr = String.valueOf(lastNextPage);
        if (!part1.isEmpty() && part1.startsWith(lastPageStr)) {
            part1 = part1.substring(lastPageStr.length());
        }

        for (int i = lastNextPage + 1; i <= totalPage; i++) {
            Map<String, Integer> extendedNextPageLink = new HashMap<>();
            String link = part0 + i + part1;
            extendedNextPageLink.put(link, i);
            nextPageLinks.add(extendedNextPageLink);
        }

        return nextPageLinks;
    }
}
