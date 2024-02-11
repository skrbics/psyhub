package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.Therapist;

/**
 * Spring Data JPA repository for the Therapist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TherapistRepository extends JpaRepository<Therapist, Long> {}
