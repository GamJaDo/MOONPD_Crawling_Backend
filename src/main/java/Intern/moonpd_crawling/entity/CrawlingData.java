package Intern.moonpd_crawling.entity;

import Intern.moonpd_crawling.dto.response.CrawlingResultResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "CRAWLING_DATA_TB")
@Getter
public class CrawlingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private Target target;

    @Column(name = "pdf_url", columnDefinition = "TEXT")
    private String pdfUrl;

    @Column(name = "title")
    private String title;

    @CreatedDate
    @Column(name = "crawling_time", nullable = true)
    private LocalDateTime crawlingTime;

    protected CrawlingData() {
    }

    public CrawlingData(Target target, String pdfUrl, String title) {
        this.target = target;
        this.pdfUrl = pdfUrl;
        this.title = title;
    }

    public void setCrawlingTime() {
        this.crawlingTime = LocalDateTime.now();
    }
}
