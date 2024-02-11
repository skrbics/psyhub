package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.MMPITest;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.MMPITest}.
 */
public interface MMPITestService {
    /**
     * Save a mMPITest.
     *
     * @param mMPITest the entity to save.
     * @return the persisted entity.
     */
    MMPITest save(MMPITest mMPITest);

    /**
     * Updates a mMPITest.
     *
     * @param mMPITest the entity to update.
     * @return the persisted entity.
     */
    MMPITest update(MMPITest mMPITest);

    /**
     * Partially updates a mMPITest.
     *
     * @param mMPITest the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MMPITest> partialUpdate(MMPITest mMPITest);

    /**
     * Get all the mMPITests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MMPITest> findAll(Pageable pageable);

    /**
     * Get the "id" mMPITest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MMPITest> findOne(Long id);

    /**
     * Delete the "id" mMPITest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
