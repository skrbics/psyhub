package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.SessionBill;

/**
 * Spring Data JPA repository for the SessionBill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionBillRepository extends JpaRepository<SessionBill, Long> {}
