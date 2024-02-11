package si.eclectic.psyhub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.psyhub.domain.MMPIFeature;

/**
 * Spring Data JPA repository for the MMPIFeature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMPIFeatureRepository extends JpaRepository<MMPIFeature, Long> {}
