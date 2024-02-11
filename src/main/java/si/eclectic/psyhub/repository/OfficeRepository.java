package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.Office;

/**
 * Spring Data JPA repository for the Office entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {}
