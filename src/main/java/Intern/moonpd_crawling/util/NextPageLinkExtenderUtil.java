package Intern.moonpd_crawling.util;

import Intern.moonpd_crawling.constants.KeywordConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NextPageLinkExtenderUtil {

    public List<Map<String, Integer>> extendedNextPageLinks(int totalPage,
        List<Map<String, Integer>> nextPageLinks) {

        String[] nextPageKeywords = KeywordConstants.NEXT_PAGE_KEYWORDS;
        String nextPageWord = null;

        Map<String, Integer> lastMap = nextPageLinks.get(nextPageLinks.size() - 1);
        String lastNextPageLink = lastMap.entrySet().iterator().next().getKey();
        int lastNextPage = lastMap.entrySet().iterator().next().getValue();

        for (String nextPageKeyword : nextPageKeywords) {
            if (lastNextPageLink.contains(nextPageKeyword)) {
                nextPageWord = nextPageKeyword;
                break;
            }
        }

        if (nextPageWord == null || nextPageWord.isEmpty()) {
            throw new NullPointerException("nextPageWord is null or empty");
        }

        int keywordIndex = lastNextPageLink.indexOf(nextPageWord);
        String part0 = lastNextPageLink.substring(0, keywordIndex + nextPageWord.length());
        String part1 = lastNextPageLink.substring(keywordIndex + nextPageWord.length());

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
