package si.eclectic.psyhub.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.SessionBill;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.SessionBill}.
 */
public interface SessionBillService {
    /**
     * Save a sessionBill.
     *
     * @param sessionBill the entity to save.
     * @return the persisted entity.
     */
    SessionBill save(SessionBill sessionBill);

    /**
     * Updates a sessionBill.
     *
     * @param sessionBill the entity to update.
     * @return the persisted entity.
     */
    SessionBill update(SessionBill sessionBill);

    /**
     * Partially updates a sessionBill.
     *
     * @param sessionBill the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SessionBill> partialUpdate(SessionBill sessionBill);

    /**
     * Get all the sessionBills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SessionBill> findAll(Pageable pageable);

    /**
     * Get all the SessionBill where Session is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SessionBill> findAllWhereSessionIsNull();

    /**
     * Get the "id" sessionBill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SessionBill> findOne(Long id);

    /**
     * Delete the "id" sessionBill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
