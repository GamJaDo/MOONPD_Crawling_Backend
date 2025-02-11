package Intern.moonpd_crawling.repository;

import Intern.moonpd_crawling.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {

    boolean existsByGroup(String group);
}
