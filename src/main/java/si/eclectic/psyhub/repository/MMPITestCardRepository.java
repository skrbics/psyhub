package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.MMPITestCard;

/**
 * Spring Data JPA repository for the MMPITestCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMPITestCardRepository extends JpaRepository<MMPITestCard, Long> {}
