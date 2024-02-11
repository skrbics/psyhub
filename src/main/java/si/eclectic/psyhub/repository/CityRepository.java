package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.City;

/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> {}
