package si.eclectic.psyhub.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.psyhub.domain.Address;

/**
 * Service Interface for managing {@link si.eclectic.psyhub.domain.Address}.
 */
public interface AddressService {
    /**
     * Save a address.
     *
     * @param address the entity to save.
     * @return the persisted entity.
     */
    Address save(Address address);

    /**
     * Updates a address.
     *
     * @param address the entity to update.
     * @return the persisted entity.
     */
    Address update(Address address);

    /**
     * Partially updates a address.
     *
     * @param address the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Address> partialUpdate(Address address);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Address> findAll(Pageable pageable);

    /**
     * Get all the Address where Client is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Address> findAllWhereClientIsNull();
    /**
     * Get all the Address where Office is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Address> findAllWhereOfficeIsNull();

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Address> findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
