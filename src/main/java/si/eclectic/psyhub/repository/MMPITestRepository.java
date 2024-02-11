package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.MMPITest;

/**
 * Spring Data JPA repository for the MMPITest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMPITestRepository extends JpaRepository<MMPITest, Long> {}
