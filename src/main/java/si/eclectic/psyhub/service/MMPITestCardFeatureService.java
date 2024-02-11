package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.MMPITestCardFeature;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.MMPITestCardFeature}.
 */
public interface MMPITestCardFeatureService {
    /**
     * Save a mMPITestCardFeature.
     *
     * @param mMPITestCardFeature the entity to save.
     * @return the persisted entity.
     */
    MMPITestCardFeature save(MMPITestCardFeature mMPITestCardFeature);

    /**
     * Updates a mMPITestCardFeature.
     *
     * @param mMPITestCardFeature the entity to update.
     * @return the persisted entity.
     */
    MMPITestCardFeature update(MMPITestCardFeature mMPITestCardFeature);

    /**
     * Partially updates a mMPITestCardFeature.
     *
     * @param mMPITestCardFeature the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MMPITestCardFeature> partialUpdate(MMPITestCardFeature mMPITestCardFeature);

    /**
     * Get all the mMPITestCardFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MMPITestCardFeature> findAll(Pageable pageable);

    /**
     * Get the "id" mMPITestCardFeature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MMPITestCardFeature> findOne(Long id);

    /**
     * Delete the "id" mMPITestCardFeature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
