package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.MMPITestCardFeature;
import si.eclectic.psyhub.repository.MMPITestCardFeatureRepository;
import si.eclectic.psyhub.service.MMPITestCardFeatureService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.MMPITestCardFeature}.
 */
@Service
@Transactional
public class MMPITestCardFeatureServiceImpl implements MMPITestCardFeatureService {

    private final Logger log = LoggerFactory.getLogger(MMPITestCardFeatureServiceImpl.class);

    private final MMPITestCardFeatureRepository mMPITestCardFeatureRepository;

    public MMPITestCardFeatureServiceImpl(MMPITestCardFeatureRepository mMPITestCardFeatureRepository) {
        this.mMPITestCardFeatureRepository = mMPITestCardFeatureRepository;
    }

    @Override
    public MMPITestCardFeature save(MMPITestCardFeature mMPITestCardFeature) {
        log.debug("Request to save MMPITestCardFeature : {}", mMPITestCardFeature);
        return mMPITestCardFeatureRepository.save(mMPITestCardFeature);
    }

    @Override
    public MMPITestCardFeature update(MMPITestCardFeature mMPITestCardFeature) {
        log.debug("Request to update MMPITestCardFeature : {}", mMPITestCardFeature);
        return mMPITestCardFeatureRepository.save(mMPITestCardFeature);
    }

    @Override
    public Optional<MMPITestCardFeature> partialUpdate(MMPITestCardFeature mMPITestCardFeature) {
        log.debug("Request to partially update MMPITestCardFeature : {}", mMPITestCardFeature);

        return mMPITestCardFeatureRepository
            .findById(mMPITestCardFeature.getId())
            .map(existingMMPITestCardFeature -> {
                if (mMPITestCardFeature.getAnswerYes() != null) {
                    existingMMPITestCardFeature.setAnswerYes(mMPITestCardFeature.getAnswerYes());
                }

                return existingMMPITestCardFeature;
            })
            .map(mMPITestCardFeatureRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MMPITestCardFeature> findAll(Pageable pageable) {
        log.debug("Request to get all MMPITestCardFeatures");
        return mMPITestCardFeatureRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MMPITestCardFeature> findOne(Long id) {
        log.debug("Request to get MMPITestCardFeature : {}", id);
        return mMPITestCardFeatureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMPITestCardFeature : {}", id);
        mMPITestCardFeatureRepository.deleteById(id);
    }
}
