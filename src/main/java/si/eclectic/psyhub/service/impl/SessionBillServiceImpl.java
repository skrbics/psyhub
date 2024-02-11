package si.eclectic.psyhub.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.SessionBill;
import si.eclectic.psyhub.repository.SessionBillRepository;
import si.eclectic.psyhub.service.SessionBillService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.SessionBill}.
 */
@Service
@Transactional
public class SessionBillServiceImpl implements SessionBillService {

    private final Logger log = LoggerFactory.getLogger(SessionBillServiceImpl.class);

    private final SessionBillRepository sessionBillRepository;

    public SessionBillServiceImpl(SessionBillRepository sessionBillRepository) {
        this.sessionBillRepository = sessionBillRepository;
    }

    @Override
    public SessionBill save(SessionBill sessionBill) {
        log.debug("Request to save SessionBill : {}", sessionBill);
        return sessionBillRepository.save(sessionBill);
    }

    @Override
    public SessionBill update(SessionBill sessionBill) {
        log.debug("Request to update SessionBill : {}", sessionBill);
        return sessionBillRepository.save(sessionBill);
    }

    @Override
    public Optional<SessionBill> partialUpdate(SessionBill sessionBill) {
        log.debug("Request to partially update SessionBill : {}", sessionBill);

        return sessionBillRepository
            .findById(sessionBill.getId())
            .map(existingSessionBill -> {
                if (sessionBill.getAmount() != null) {
                    existingSessionBill.setAmount(sessionBill.getAmount());
                }
                if (sessionBill.getPaid() != null) {
                    existingSessionBill.setPaid(sessionBill.getPaid());
                }

                return existingSessionBill;
            })
            .map(sessionBillRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SessionBill> findAll(Pageable pageable) {
        log.debug("Request to get all SessionBills");
        return sessionBillRepository.findAll(pageable);
    }

    /**
     *  Get all the sessionBills where Session is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SessionBill> findAllWhereSessionIsNull() {
        log.debug("Request to get all sessionBills where Session is null");
        return StreamSupport
            .stream(sessionBillRepository.findAll().spliterator(), false)
            .filter(sessionBill -> sessionBill.getSession() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SessionBill> findOne(Long id) {
        log.debug("Request to get SessionBill : {}", id);
        return sessionBillRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SessionBill : {}", id);
        sessionBillRepository.deleteById(id);
    }
}
