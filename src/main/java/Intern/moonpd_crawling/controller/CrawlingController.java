package Intern.moonpd_crawling.controller;

import Intern.moonpd_crawling.service.CrawlingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crawling")
public class CrawlingController {

    private final CrawlingService crawlingService;

    public CrawlingController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @PostMapping
    public ResponseEntity<Void> startCrawling() {
        crawlingService.startCrawling();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
