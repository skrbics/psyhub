package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.MMPIFeature;
import si.eclectic.psyhub.repository.MMPIFeatureRepository;
import si.eclectic.psyhub.service.MMPIFeatureService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.MMPIFeature}.
 */
@Service
@Transactional
public class MMPIFeatureServiceImpl implements MMPIFeatureService {

    private final Logger log = LoggerFactory.getLogger(MMPIFeatureServiceImpl.class);

    private final MMPIFeatureRepository mMPIFeatureRepository;

    public MMPIFeatureServiceImpl(MMPIFeatureRepository mMPIFeatureRepository) {
        this.mMPIFeatureRepository = mMPIFeatureRepository;
    }

    @Override
    public MMPIFeature save(MMPIFeature mMPIFeature) {
        log.debug("Request to save MMPIFeature : {}", mMPIFeature);
        return mMPIFeatureRepository.save(mMPIFeature);
    }

    @Override
    public MMPIFeature update(MMPIFeature mMPIFeature) {
        log.debug("Request to update MMPIFeature : {}", mMPIFeature);
        return mMPIFeatureRepository.save(mMPIFeature);
    }

    @Override
    public Optional<MMPIFeature> partialUpdate(MMPIFeature mMPIFeature) {
        log.debug("Request to partially update MMPIFeature : {}", mMPIFeature);

        return mMPIFeatureRepository
            .findById(mMPIFeature.getId())
            .map(existingMMPIFeature -> {
                if (mMPIFeature.getName() != null) {
                    existingMMPIFeature.setName(mMPIFeature.getName());
                }

                return existingMMPIFeature;
            })
            .map(mMPIFeatureRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MMPIFeature> findAll(Pageable pageable) {
        log.debug("Request to get all MMPIFeatures");
        return mMPIFeatureRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MMPIFeature> findOne(Long id) {
        log.debug("Request to get MMPIFeature : {}", id);
        return mMPIFeatureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMPIFeature : {}", id);
        mMPIFeatureRepository.deleteById(id);
    }
}
