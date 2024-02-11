package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.eclectic.psyhub.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
