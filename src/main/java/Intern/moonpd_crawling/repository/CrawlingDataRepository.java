package Intern.moonpd_crawling.repository;

import Intern.moonpd_crawling.entity.CrawlingData;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlingDataRepository extends JpaRepository<CrawlingData, Long> {

    boolean existsByPdfUrl(String pdfUrl);

    int countByTargetId(Long targetId);

    @Query("SELECT MAX(cd.crawlingTime) FROM CrawlingData cd WHERE cd.target.id = :targetId")
    LocalDateTime findLatestCrawlingTimeByTargetId(@Param("targetId") Long targetId);
}
