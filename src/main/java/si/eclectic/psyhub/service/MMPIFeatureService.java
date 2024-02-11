package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.MMPIFeature;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.MMPIFeature}.
 */
public interface MMPIFeatureService {
    /**
     * Save a mMPIFeature.
     *
     * @param mMPIFeature the entity to save.
     * @return the persisted entity.
     */
    MMPIFeature save(MMPIFeature mMPIFeature);

    /**
     * Updates a mMPIFeature.
     *
     * @param mMPIFeature the entity to update.
     * @return the persisted entity.
     */
    MMPIFeature update(MMPIFeature mMPIFeature);

    /**
     * Partially updates a mMPIFeature.
     *
     * @param mMPIFeature the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MMPIFeature> partialUpdate(MMPIFeature mMPIFeature);

    /**
     * Get all the mMPIFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MMPIFeature> findAll(Pageable pageable);

    /**
     * Get the "id" mMPIFeature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MMPIFeature> findOne(Long id);

    /**
     * Delete the "id" mMPIFeature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
