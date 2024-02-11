package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.MMPITestCard;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.MMPITestCard}.
 */
public interface MMPITestCardService {
    /**
     * Save a mMPITestCard.
     *
     * @param mMPITestCard the entity to save.
     * @return the persisted entity.
     */
    MMPITestCard save(MMPITestCard mMPITestCard);

    /**
     * Updates a mMPITestCard.
     *
     * @param mMPITestCard the entity to update.
     * @return the persisted entity.
     */
    MMPITestCard update(MMPITestCard mMPITestCard);

    /**
     * Partially updates a mMPITestCard.
     *
     * @param mMPITestCard the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MMPITestCard> partialUpdate(MMPITestCard mMPITestCard);

    /**
     * Get all the mMPITestCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MMPITestCard> findAll(Pageable pageable);

    /**
     * Get the "id" mMPITestCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MMPITestCard> findOne(Long id);

    /**
     * Delete the "id" mMPITestCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
