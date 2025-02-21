package Intern.moonpd_crawling.util;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ElementCountUtil {

    public int getTotalYearCnt(List<String> yearLinks) {

        return yearLinks.size();
    }

    public int getTotalPageCnt(List<String> nextPageLinks) {

        if (nextPageLinks.isEmpty()){
            return 1;
        }
        else {
            return nextPageLinks.size();
        }
    }
}
