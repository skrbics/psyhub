package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.Country;

/**
 * Spring Data JPA repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {}
