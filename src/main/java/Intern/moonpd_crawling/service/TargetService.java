package Intern.moonpd_crawling.service;

import Intern.moonpd_crawling.dto.request.TargetRegisterRequest;
import Intern.moonpd_crawling.dto.request.TargetUpdateRequest;
import Intern.moonpd_crawling.dto.response.CrawlingResultResponse;
import Intern.moonpd_crawling.dto.response.TargetViewResponse;
import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.exception.DuplicateException;
import Intern.moonpd_crawling.exception.NotFoundException;
import Intern.moonpd_crawling.repository.CrawlingDataRepository;
import Intern.moonpd_crawling.repository.TargetRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TargetService {

    private final TargetRepository targetRepository;
    private final CrawlingDataRepository crawlingDataRepository;

    public TargetService(TargetRepository targetRepository,
        CrawlingDataRepository crawlingDataRepository) {
        this.targetRepository = targetRepository;
        this.crawlingDataRepository = crawlingDataRepository;
    }

    public List<TargetViewResponse> getTarget() {
        return targetRepository.findAll().stream().map(Target::toDto).toList();
    }

    public void registerTarget(TargetRegisterRequest request) {
        registerEntity(request.toEntity());
    }

    public void updateTarget(Long targetId, TargetUpdateRequest request) {
        validateEntityId(targetId);
        updateEntityById(targetId, request.toEntity());
    }

    public CrawlingResultResponse crawlingResultTarget(Long targetId) {
        int totalCount = crawlingDataRepository.countByTargetId(targetId);
        LocalDateTime latestCrawlingTime = crawlingDataRepository.findLatestCrawlingTimeByTargetId(targetId);

        return new CrawlingResultResponse(targetId, latestCrawlingTime, totalCount);
    }

    /*
    public void downloadTarget() {

    }
     */

    private void registerEntity(Target target) {
        checkDuplicateGroup(target.getGroup());
        try {
            target.setRegisterTime();
            targetRepository.save(target);
        } catch (Exception e) {
            throw new NotFoundException("Failed to save");
        }
    }

    private void checkDuplicateGroup(String group) {
        if (targetRepository.existsByGroup(group)) {
            throw new DuplicateException("Group '" + group + "' already exists.");
        }
    }

    private void validateEntityId(Long targetId) {
        if (targetRepository.findById(targetId).isEmpty()) {
            throw new NotFoundException("The corresponding object does not exist.");
        }
    }

    private void updateEntityById(Long targetId, Target target) {
        Target existing = targetRepository.findById(targetId)
            .orElseThrow(() -> new NotFoundException(
                "The corresponding Culture Foundation does not exist."));
        existing.update(target);
        existing.setUpdateTime();
        targetRepository.save(existing);
    }
}
