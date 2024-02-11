package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.Office;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.Office}.
 */
public interface OfficeService {
    /**
     * Save a office.
     *
     * @param office the entity to save.
     * @return the persisted entity.
     */
    Office save(Office office);

    /**
     * Updates a office.
     *
     * @param office the entity to update.
     * @return the persisted entity.
     */
    Office update(Office office);

    /**
     * Partially updates a office.
     *
     * @param office the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Office> partialUpdate(Office office);

    /**
     * Get all the offices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Office> findAll(Pageable pageable);

    /**
     * Get the "id" office.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Office> findOne(Long id);

    /**
     * Delete the "id" office.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
