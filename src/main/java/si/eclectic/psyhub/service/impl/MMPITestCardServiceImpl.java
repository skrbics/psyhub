package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.MMPITestCard;
import si.eclectic.psyhub.repository.MMPITestCardRepository;
import si.eclectic.psyhub.service.MMPITestCardService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.MMPITestCard}.
 */
@Service
@Transactional
public class MMPITestCardServiceImpl implements MMPITestCardService {

    private final Logger log = LoggerFactory.getLogger(MMPITestCardServiceImpl.class);

    private final MMPITestCardRepository mMPITestCardRepository;

    public MMPITestCardServiceImpl(MMPITestCardRepository mMPITestCardRepository) {
        this.mMPITestCardRepository = mMPITestCardRepository;
    }

    @Override
    public MMPITestCard save(MMPITestCard mMPITestCard) {
        log.debug("Request to save MMPITestCard : {}", mMPITestCard);
        return mMPITestCardRepository.save(mMPITestCard);
    }

    @Override
    public MMPITestCard update(MMPITestCard mMPITestCard) {
        log.debug("Request to update MMPITestCard : {}", mMPITestCard);
        return mMPITestCardRepository.save(mMPITestCard);
    }

    @Override
    public Optional<MMPITestCard> partialUpdate(MMPITestCard mMPITestCard) {
        log.debug("Request to partially update MMPITestCard : {}", mMPITestCard);

        return mMPITestCardRepository
            .findById(mMPITestCard.getId())
            .map(existingMMPITestCard -> {
                if (mMPITestCard.getQuestion() != null) {
                    existingMMPITestCard.setQuestion(mMPITestCard.getQuestion());
                }

                return existingMMPITestCard;
            })
            .map(mMPITestCardRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MMPITestCard> findAll(Pageable pageable) {
        log.debug("Request to get all MMPITestCards");
        return mMPITestCardRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MMPITestCard> findOne(Long id) {
        log.debug("Request to get MMPITestCard : {}", id);
        return mMPITestCardRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMPITestCard : {}", id);
        mMPITestCardRepository.deleteById(id);
    }
}
