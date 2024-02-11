package si.eclectic.psyhub.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.Currency;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.Currency}.
 */
public interface CurrencyService {
    /**
     * Save a currency.
     *
     * @param currency the entity to save.
     * @return the persisted entity.
     */
    Currency save(Currency currency);

    /**
     * Updates a currency.
     *
     * @param currency the entity to update.
     * @return the persisted entity.
     */
    Currency update(Currency currency);

    /**
     * Partially updates a currency.
     *
     * @param currency the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Currency> partialUpdate(Currency currency);

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Currency> findAll(Pageable pageable);

    /**
     * Get the "id" currency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Currency> findOne(Long id);

    /**
     * Delete the "id" currency.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
