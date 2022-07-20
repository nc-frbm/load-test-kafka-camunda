package dk.load.test.ermis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingStatusRepository extends JpaRepository<ProcessingStatus, Long> {
}
