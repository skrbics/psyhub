package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.Session;
import si.eclectic.psyhub.repository.SessionRepository;
import si.eclectic.psyhub.service.SessionService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.Session}.
 */
@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        log.debug("Request to save Session : {}", session);
        return sessionRepository.save(session);
    }

    @Override
    public Session update(Session session) {
        log.debug("Request to update Session : {}", session);
        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> partialUpdate(Session session) {
        log.debug("Request to partially update Session : {}", session);

        return sessionRepository
            .findById(session.getId())
            .map(existingSession -> {
                if (session.getDate() != null) {
                    existingSession.setDate(session.getDate());
                }
                if (session.getLocation() != null) {
                    existingSession.setLocation(session.getLocation());
                }
                if (session.getNotes() != null) {
                    existingSession.setNotes(session.getNotes());
                }
                if (session.getCompleted() != null) {
                    existingSession.setCompleted(session.getCompleted());
                }

                return existingSession;
            })
            .map(sessionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Session> findAll(Pageable pageable) {
        log.debug("Request to get all Sessions");
        return sessionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Session> findOne(Long id) {
        log.debug("Request to get Session : {}", id);
        return sessionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Session : {}", id);
        sessionRepository.deleteById(id);
    }
}
