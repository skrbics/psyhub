package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.MMPITest;
import si.eclectic.psyhub.repository.MMPITestRepository;
import si.eclectic.psyhub.service.MMPITestService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.MMPITest}.
 */
@Service
@Transactional
public class MMPITestServiceImpl implements MMPITestService {

    private final Logger log = LoggerFactory.getLogger(MMPITestServiceImpl.class);

    private final MMPITestRepository mMPITestRepository;

    public MMPITestServiceImpl(MMPITestRepository mMPITestRepository) {
        this.mMPITestRepository = mMPITestRepository;
    }

    @Override
    public MMPITest save(MMPITest mMPITest) {
        log.debug("Request to save MMPITest : {}", mMPITest);
        return mMPITestRepository.save(mMPITest);
    }

    @Override
    public MMPITest update(MMPITest mMPITest) {
        log.debug("Request to update MMPITest : {}", mMPITest);
        return mMPITestRepository.save(mMPITest);
    }

    @Override
    public Optional<MMPITest> partialUpdate(MMPITest mMPITest) {
        log.debug("Request to partially update MMPITest : {}", mMPITest);

        return mMPITestRepository
            .findById(mMPITest.getId())
            .map(existingMMPITest -> {
                if (mMPITest.getOrder() != null) {
                    existingMMPITest.setOrder(mMPITest.getOrder());
                }
                if (mMPITest.getDate() != null) {
                    existingMMPITest.setDate(mMPITest.getDate());
                }

                return existingMMPITest;
            })
            .map(mMPITestRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MMPITest> findAll(Pageable pageable) {
        log.debug("Request to get all MMPITests");
        return mMPITestRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MMPITest> findOne(Long id) {
        log.debug("Request to get MMPITest : {}", id);
        return mMPITestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MMPITest : {}", id);
        mMPITestRepository.deleteById(id);
    }
}
