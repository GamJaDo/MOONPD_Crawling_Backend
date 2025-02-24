package Intern.moonpd_crawling.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ElementCountUtil {

    public int getTotalYearCnt(List<Map<String, Integer>> yearLinks) {

        return yearLinks.size();
    }

    public int getTotalPageCnt(List<Map<String, Integer>> nextPageLinks) {

        if (nextPageLinks.isEmpty()) {

            return 1;
        } else {

            return nextPageLinks.size();
        }
    }

    public List<Map<String, Integer>> checkFirstNextPageLink(List<Map<String, Integer>> nextPageLinks) {

        Map<String, Integer> firstMap = nextPageLinks.get(0);
        Integer firstValue = firstMap.entrySet().iterator().next().getValue();

        if (firstValue.equals(2)) {
            Map<String, Integer> dummyNexPageLink = new HashMap<>();
            dummyNexPageLink.put("dummyNextPageLink", 1);
            nextPageLinks.add(0, dummyNexPageLink);
        }

        return nextPageLinks;
    }
}
