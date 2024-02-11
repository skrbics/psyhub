package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.Therapist;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.Therapist}.
 */
public interface TherapistService {
    /**
     * Save a therapist.
     *
     * @param therapist the entity to save.
     * @return the persisted entity.
     */
    Therapist save(Therapist therapist);

    /**
     * Updates a therapist.
     *
     * @param therapist the entity to update.
     * @return the persisted entity.
     */
    Therapist update(Therapist therapist);

    /**
     * Partially updates a therapist.
     *
     * @param therapist the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Therapist> partialUpdate(Therapist therapist);

    /**
     * Get all the therapists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Therapist> findAll(Pageable pageable);

    /**
     * Get the "id" therapist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Therapist> findOne(Long id);

    /**
     * Delete the "id" therapist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
