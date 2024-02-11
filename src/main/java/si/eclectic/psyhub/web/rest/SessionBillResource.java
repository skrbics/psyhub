package si.eclectic.psyhub.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import si.eclectic.psyhub.domain.SessionBill;
import si.eclectic.psyhub.repository.SessionBillRepository;
import si.eclectic.psyhub.service.SessionBillService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.SessionBill}.
 */
@RestController
@RequestMapping("/api/session-bills")
public class SessionBillResource {

    private final Logger log = LoggerFactory.getLogger(SessionBillResource.class);

    private static final String ENTITY_NAME = "sessionBill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionBillService sessionBillService;

    private final SessionBillRepository sessionBillRepository;

    public SessionBillResource(SessionBillService sessionBillService, SessionBillRepository sessionBillRepository) {
        this.sessionBillService = sessionBillService;
        this.sessionBillRepository = sessionBillRepository;
    }

    /**
     * {@code POST  /session-bills} : Create a new sessionBill.
     *
     * @param sessionBill the sessionBill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionBill, or with status {@code 400 (Bad Request)} if the sessionBill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SessionBill> createSessionBill(@RequestBody SessionBill sessionBill) throws URISyntaxException {
        log.debug("REST request to save SessionBill : {}", sessionBill);
        if (sessionBill.getId() != null) {
            throw new BadRequestAlertException("A new sessionBill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionBill result = sessionBillService.save(sessionBill);
        return ResponseEntity
            .created(new URI("/api/session-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /session-bills/:id} : Updates an existing sessionBill.
     *
     * @param id the id of the sessionBill to save.
     * @param sessionBill the sessionBill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionBill,
     * or with status {@code 400 (Bad Request)} if the sessionBill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionBill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SessionBill> updateSessionBill(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SessionBill sessionBill
    ) throws URISyntaxException {
        log.debug("REST request to update SessionBill : {}, {}", id, sessionBill);
        if (sessionBill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionBill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionBillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SessionBill result = sessionBillService.update(sessionBill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionBill.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /session-bills/:id} : Partial updates given fields of an existing sessionBill, field will ignore if it is null
     *
     * @param id the id of the sessionBill to save.
     * @param sessionBill the sessionBill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionBill,
     * or with status {@code 400 (Bad Request)} if the sessionBill is not valid,
     * or with status {@code 404 (Not Found)} if the sessionBill is not found,
     * or with status {@code 500 (Internal Server Error)} if the sessionBill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SessionBill> partialUpdateSessionBill(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SessionBill sessionBill
    ) throws URISyntaxException {
        log.debug("REST request to partial update SessionBill partially : {}, {}", id, sessionBill);
        if (sessionBill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionBill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionBillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SessionBill> result = sessionBillService.partialUpdate(sessionBill);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionBill.getId().toString())
        );
    }

    /**
     * {@code GET  /session-bills} : get all the sessionBills.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionBills in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SessionBill>> getAllSessionBills(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("session-is-null".equals(filter)) {
            log.debug("REST request to get all SessionBills where session is null");
            return new ResponseEntity<>(sessionBillService.findAllWhereSessionIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of SessionBills");
        Page<SessionBill> page = sessionBillService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /session-bills/:id} : get the "id" sessionBill.
     *
     * @param id the id of the sessionBill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionBill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionBill> getSessionBill(@PathVariable("id") Long id) {
        log.debug("REST request to get SessionBill : {}", id);
        Optional<SessionBill> sessionBill = sessionBillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionBill);
    }

    /**
     * {@code DELETE  /session-bills/:id} : delete the "id" sessionBill.
     *
     * @param id the id of the sessionBill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionBill(@PathVariable("id") Long id) {
        log.debug("REST request to delete SessionBill : {}", id);
        sessionBillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
