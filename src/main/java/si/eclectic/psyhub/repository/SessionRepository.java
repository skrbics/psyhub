package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.Session;

/**
 * Spring Data JPA repository for the Session entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {}
