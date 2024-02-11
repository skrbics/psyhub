package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.MMPIAnswer;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.MMPIAnswer}.
 */
public interface MMPIAnswerService {
    /**
     * Save a mMPIAnswer.
     *
     * @param mMPIAnswer the entity to save.
     * @return the persisted entity.
     */
    MMPIAnswer save(MMPIAnswer mMPIAnswer);

    /**
     * Updates a mMPIAnswer.
     *
     * @param mMPIAnswer the entity to update.
     * @return the persisted entity.
     */
    MMPIAnswer update(MMPIAnswer mMPIAnswer);

    /**
     * Partially updates a mMPIAnswer.
     *
     * @param mMPIAnswer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MMPIAnswer> partialUpdate(MMPIAnswer mMPIAnswer);

    /**
     * Get all the mMPIAnswers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MMPIAnswer> findAll(Pageable pageable);

    /**
     * Get the "id" mMPIAnswer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MMPIAnswer> findOne(Long id);

    /**
     * Delete the "id" mMPIAnswer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
