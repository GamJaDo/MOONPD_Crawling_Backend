package Intern.moonpd_crawling.controller;

import Intern.moonpd_crawling.dto.request.TargetRegisterRequest;
import Intern.moonpd_crawling.dto.request.TargetUpdateRequest;
import Intern.moonpd_crawling.dto.response.CrawlingResultResponse;
import Intern.moonpd_crawling.dto.response.TargetViewResponse;
import Intern.moonpd_crawling.service.TargetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/target")
public class TargetController {

    private final TargetService targetService;

    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    @GetMapping("/view")
    public ResponseEntity<List<TargetViewResponse>> viewTarget() {
        List<TargetViewResponse> responses = targetService.getTarget();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerTarget(@RequestBody TargetRegisterRequest request) {
        targetService.registerTarget(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<Void> updateTarget(@PathVariable(name = "targetId") Long targetId,
        @RequestBody TargetUpdateRequest request) {
        targetService.updateTarget(targetId, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{targetId}")
    public ResponseEntity<CrawlingResultResponse> crawlingResultTarget(
        @PathVariable(name = "targetId") Long targetId) {
        CrawlingResultResponse response = targetService.crawlingResultTarget(targetId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
