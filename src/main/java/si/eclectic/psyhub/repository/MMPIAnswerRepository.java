package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.MMPIAnswer;

/**
 * Spring Data JPA repository for the MMPIAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMPIAnswerRepository extends JpaRepository<MMPIAnswer, Long> {}
