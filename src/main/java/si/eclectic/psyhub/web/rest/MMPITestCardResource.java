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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import si.eclectic.psyhub.domain.MMPITestCard;
import si.eclectic.psyhub.repository.MMPITestCardRepository;
import si.eclectic.psyhub.service.MMPITestCardService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.MMPITestCard}.
 */
@RestController
@RequestMapping("/api/mmpi-test-cards")
public class MMPITestCardResource {

    private final Logger log = LoggerFactory.getLogger(MMPITestCardResource.class);

    private static final String ENTITY_NAME = "mMPITestCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MMPITestCardService mMPITestCardService;

    private final MMPITestCardRepository mMPITestCardRepository;

    public MMPITestCardResource(MMPITestCardService mMPITestCardService, MMPITestCardRepository mMPITestCardRepository) {
        this.mMPITestCardService = mMPITestCardService;
        this.mMPITestCardRepository = mMPITestCardRepository;
    }

    /**
     * {@code POST  /mmpi-test-cards} : Create a new mMPITestCard.
     *
     * @param mMPITestCard the mMPITestCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mMPITestCard, or with status {@code 400 (Bad Request)} if the mMPITestCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MMPITestCard> createMMPITestCard(@RequestBody MMPITestCard mMPITestCard) throws URISyntaxException {
        log.debug("REST request to save MMPITestCard : {}", mMPITestCard);
        if (mMPITestCard.getId() != null) {
            throw new BadRequestAlertException("A new mMPITestCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMPITestCard result = mMPITestCardService.save(mMPITestCard);
        return ResponseEntity
            .created(new URI("/api/mmpi-test-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmpi-test-cards/:id} : Updates an existing mMPITestCard.
     *
     * @param id the id of the mMPITestCard to save.
     * @param mMPITestCard the mMPITestCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPITestCard,
     * or with status {@code 400 (Bad Request)} if the mMPITestCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mMPITestCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MMPITestCard> updateMMPITestCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPITestCard mMPITestCard
    ) throws URISyntaxException {
        log.debug("REST request to update MMPITestCard : {}, {}", id, mMPITestCard);
        if (mMPITestCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPITestCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPITestCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MMPITestCard result = mMPITestCardService.update(mMPITestCard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPITestCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmpi-test-cards/:id} : Partial updates given fields of an existing mMPITestCard, field will ignore if it is null
     *
     * @param id the id of the mMPITestCard to save.
     * @param mMPITestCard the mMPITestCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPITestCard,
     * or with status {@code 400 (Bad Request)} if the mMPITestCard is not valid,
     * or with status {@code 404 (Not Found)} if the mMPITestCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the mMPITestCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MMPITestCard> partialUpdateMMPITestCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPITestCard mMPITestCard
    ) throws URISyntaxException {
        log.debug("REST request to partial update MMPITestCard partially : {}, {}", id, mMPITestCard);
        if (mMPITestCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPITestCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPITestCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MMPITestCard> result = mMPITestCardService.partialUpdate(mMPITestCard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPITestCard.getId().toString())
        );
    }

    /**
     * {@code GET  /mmpi-test-cards} : get all the mMPITestCards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMPITestCards in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MMPITestCard>> getAllMMPITestCards(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MMPITestCards");
        Page<MMPITestCard> page = mMPITestCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mmpi-test-cards/:id} : get the "id" mMPITestCard.
     *
     * @param id the id of the mMPITestCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mMPITestCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MMPITestCard> getMMPITestCard(@PathVariable("id") Long id) {
        log.debug("REST request to get MMPITestCard : {}", id);
        Optional<MMPITestCard> mMPITestCard = mMPITestCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMPITestCard);
    }

    /**
     * {@code DELETE  /mmpi-test-cards/:id} : delete the "id" mMPITestCard.
     *
     * @param id the id of the mMPITestCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMMPITestCard(@PathVariable("id") Long id) {
        log.debug("REST request to delete MMPITestCard : {}", id);
        mMPITestCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
