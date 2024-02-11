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
import si.eclectic.psyhub.domain.Therapist;
import si.eclectic.psyhub.repository.TherapistRepository;
import si.eclectic.psyhub.service.TherapistService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.Therapist}.
 */
@RestController
@RequestMapping("/api/therapists")
public class TherapistResource {

    private final Logger log = LoggerFactory.getLogger(TherapistResource.class);

    private static final String ENTITY_NAME = "therapist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TherapistService therapistService;

    private final TherapistRepository therapistRepository;

    public TherapistResource(TherapistService therapistService, TherapistRepository therapistRepository) {
        this.therapistService = therapistService;
        this.therapistRepository = therapistRepository;
    }

    /**
     * {@code POST  /therapists} : Create a new therapist.
     *
     * @param therapist the therapist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new therapist, or with status {@code 400 (Bad Request)} if the therapist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Therapist> createTherapist(@RequestBody Therapist therapist) throws URISyntaxException {
        log.debug("REST request to save Therapist : {}", therapist);
        if (therapist.getId() != null) {
            throw new BadRequestAlertException("A new therapist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Therapist result = therapistService.save(therapist);
        return ResponseEntity
            .created(new URI("/api/therapists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /therapists/:id} : Updates an existing therapist.
     *
     * @param id the id of the therapist to save.
     * @param therapist the therapist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated therapist,
     * or with status {@code 400 (Bad Request)} if the therapist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the therapist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Therapist> updateTherapist(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Therapist therapist
    ) throws URISyntaxException {
        log.debug("REST request to update Therapist : {}, {}", id, therapist);
        if (therapist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, therapist.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!therapistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Therapist result = therapistService.update(therapist);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, therapist.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /therapists/:id} : Partial updates given fields of an existing therapist, field will ignore if it is null
     *
     * @param id the id of the therapist to save.
     * @param therapist the therapist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated therapist,
     * or with status {@code 400 (Bad Request)} if the therapist is not valid,
     * or with status {@code 404 (Not Found)} if the therapist is not found,
     * or with status {@code 500 (Internal Server Error)} if the therapist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Therapist> partialUpdateTherapist(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Therapist therapist
    ) throws URISyntaxException {
        log.debug("REST request to partial update Therapist partially : {}, {}", id, therapist);
        if (therapist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, therapist.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!therapistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Therapist> result = therapistService.partialUpdate(therapist);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, therapist.getId().toString())
        );
    }

    /**
     * {@code GET  /therapists} : get all the therapists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of therapists in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Therapist>> getAllTherapists(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Therapists");
        Page<Therapist> page = therapistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /therapists/:id} : get the "id" therapist.
     *
     * @param id the id of the therapist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the therapist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Therapist> getTherapist(@PathVariable("id") Long id) {
        log.debug("REST request to get Therapist : {}", id);
        Optional<Therapist> therapist = therapistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(therapist);
    }

    /**
     * {@code DELETE  /therapists/:id} : delete the "id" therapist.
     *
     * @param id the id of the therapist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTherapist(@PathVariable("id") Long id) {
        log.debug("REST request to delete Therapist : {}", id);
        therapistService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
