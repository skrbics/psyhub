package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.Therapist;
import si.eclectic.psyhub.repository.TherapistRepository;
import si.eclectic.psyhub.service.TherapistService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.Therapist}.
 */
@Service
@Transactional
public class TherapistServiceImpl implements TherapistService {

    private final Logger log = LoggerFactory.getLogger(TherapistServiceImpl.class);

    private final TherapistRepository therapistRepository;

    public TherapistServiceImpl(TherapistRepository therapistRepository) {
        this.therapistRepository = therapistRepository;
    }

    @Override
    public Therapist save(Therapist therapist) {
        log.debug("Request to save Therapist : {}", therapist);
        return therapistRepository.save(therapist);
    }

    @Override
    public Therapist update(Therapist therapist) {
        log.debug("Request to update Therapist : {}", therapist);
        return therapistRepository.save(therapist);
    }

    @Override
    public Optional<Therapist> partialUpdate(Therapist therapist) {
        log.debug("Request to partially update Therapist : {}", therapist);

        return therapistRepository
            .findById(therapist.getId())
            .map(existingTherapist -> {
                if (therapist.getFirstName() != null) {
                    existingTherapist.setFirstName(therapist.getFirstName());
                }
                if (therapist.getMiddleName() != null) {
                    existingTherapist.setMiddleName(therapist.getMiddleName());
                }
                if (therapist.getLastName() != null) {
                    existingTherapist.setLastName(therapist.getLastName());
                }
                if (therapist.getUserid() != null) {
                    existingTherapist.setUserid(therapist.getUserid());
                }

                return existingTherapist;
            })
            .map(therapistRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Therapist> findAll(Pageable pageable) {
        log.debug("Request to get all Therapists");
        return therapistRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Therapist> findOne(Long id) {
        log.debug("Request to get Therapist : {}", id);
        return therapistRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Therapist : {}", id);
        therapistRepository.deleteById(id);
    }
}
