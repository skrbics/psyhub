package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.Office;
import si.eclectic.psyhub.repository.OfficeRepository;
import si.eclectic.psyhub.service.OfficeService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.Office}.
 */
@Service
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeRepository officeRepository;

    public OfficeServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public Office save(Office office) {
        log.debug("Request to save Office : {}", office);
        return officeRepository.save(office);
    }

    @Override
    public Office update(Office office) {
        log.debug("Request to update Office : {}", office);
        return officeRepository.save(office);
    }

    @Override
    public Optional<Office> partialUpdate(Office office) {
        log.debug("Request to partially update Office : {}", office);

        return officeRepository
            .findById(office.getId())
            .map(existingOffice -> {
                if (office.getOfficeName() != null) {
                    existingOffice.setOfficeName(office.getOfficeName());
                }
                if (office.getWebsite() != null) {
                    existingOffice.setWebsite(office.getWebsite());
                }
                if (office.getEmail() != null) {
                    existingOffice.setEmail(office.getEmail());
                }
                if (office.getPhone() != null) {
                    existingOffice.setPhone(office.getPhone());
                }

                return existingOffice;
            })
            .map(officeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Office> findAll(Pageable pageable) {
        log.debug("Request to get all Offices");
        return officeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Office> findOne(Long id) {
        log.debug("Request to get Office : {}", id);
        return officeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Office : {}", id);
        officeRepository.deleteById(id);
    }
}
