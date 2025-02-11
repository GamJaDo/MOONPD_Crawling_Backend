package Intern.moonpd_crawling.dto.response;

import java.time.LocalDateTime;

public record CrawlingResultResponse(Long id, LocalDateTime time, int cnt) {

}
