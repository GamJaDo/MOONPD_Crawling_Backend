package Intern.moonpd_crawling.util;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ElementCountUtil {

    public int getTotalYearCnt(List<String> yearLinks) {

        return yearLinks.size();
    }

    public int getTotalPageCnt(List<String> nextPageLinks) {

        int totalNextPageCount = nextPageLinks.size();

        if (totalNextPageCount == 0) {
            return 1;
        }
        return totalNextPageCount;
    }
}
