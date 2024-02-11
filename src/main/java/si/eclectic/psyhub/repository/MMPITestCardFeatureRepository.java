package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.MMPITestCardFeature;

/**
 * Spring Data JPA repository for the MMPITestCardFeature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMPITestCardFeatureRepository extends JpaRepository<MMPITestCardFeature, Long> {}
