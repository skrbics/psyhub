package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.MMPIAnswer;
import si.eclectic.psyhub.repository.MMPIAnswerRepository;
import si.eclectic.psyhub.service.MMPIAnswerService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.MMPIAnswer}.
 */
@Service
@Transactional
public class MMPIAnswerServiceImpl implements MMPIAnswerService {

    private final Logger log = LoggerFactory.getLogger(MMPIAnswerServiceImpl.class);

    private final MMPIAnswerRepository mMPIAnswerRepository;

    public MMPIAnswerServiceImpl(MMPIAnswerRepository mMPIAnswerRepository) {
        this.mMPIAnswerRepository = mMPIAnswerRepository;
    }

    @Override
    public MMPIAnswer save(MMPIAnswer mMPIAnswer) {
        log.debug("Request to save MMPIAnswer : {}", mMPIAnswer);
        return mMPIAnswerRepository.save(mMPIAnswer);
    }

    @Override
    public MMPIAnswer update(MMPIAnswer mMPIAnswer) {
        log.debug("Request to update MMPIAnswer : {}", mMPIAnswer);
        return mMPIAnswerRepository.save(mMPIAnswer);
    }

    @Override
    public Optional<MMPIAnswer> partialUpdate(MMPIAnswer mMPIAnswer) {
        log.debug("Request to partially update MMPIAnswer : {}", mMPIAnswer);

        return mMPIAnswerRepository
            .findById(mMPIAnswer.getId())
            .map(existingMMPIAnswer -> {
                if (mMPIAnswer.getAnsweredYes() != null) {
                    existingMMPIAnswer.setAnsweredYes(mMPIAnswer.getAnsweredYes());
                }

                return existingMMPIAnswer;
            })
            .map(mMPIAnswerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MMPIAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all MMPIAnswers");
        return mMPIAnswerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MMPIAnswer> findOne(Long id) {
        log.debug("Request to get MMPIAnswer : {}", id);
        return mMPIAnswerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMPIAnswer : {}", id);
        mMPIAnswerRepository.deleteById(id);
    }
}
