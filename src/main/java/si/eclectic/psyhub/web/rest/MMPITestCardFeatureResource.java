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
import si.eclectic.psyhub.domain.MMPITestCardFeature;
import si.eclectic.psyhub.repository.MMPITestCardFeatureRepository;
import si.eclectic.psyhub.service.MMPITestCardFeatureService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.MMPITestCardFeature}.
 */
@RestController
@RequestMapping("/api/mmpi-test-card-features")
public class MMPITestCardFeatureResource {

    private final Logger log = LoggerFactory.getLogger(MMPITestCardFeatureResource.class);

    private static final String ENTITY_NAME = "mMPITestCardFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MMPITestCardFeatureService mMPITestCardFeatureService;

    private final MMPITestCardFeatureRepository mMPITestCardFeatureRepository;

    public MMPITestCardFeatureResource(
        MMPITestCardFeatureService mMPITestCardFeatureService,
        MMPITestCardFeatureRepository mMPITestCardFeatureRepository
    ) {
        this.mMPITestCardFeatureService = mMPITestCardFeatureService;
        this.mMPITestCardFeatureRepository = mMPITestCardFeatureRepository;
    }

    /**
     * {@code POST  /mmpi-test-card-features} : Create a new mMPITestCardFeature.
     *
     * @param mMPITestCardFeature the mMPITestCardFeature to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mMPITestCardFeature, or with status {@code 400 (Bad Request)} if the mMPITestCardFeature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MMPITestCardFeature> createMMPITestCardFeature(@RequestBody MMPITestCardFeature mMPITestCardFeature)
        throws URISyntaxException {
        log.debug("REST request to save MMPITestCardFeature : {}", mMPITestCardFeature);
        if (mMPITestCardFeature.getId() != null) {
            throw new BadRequestAlertException("A new mMPITestCardFeature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMPITestCardFeature result = mMPITestCardFeatureService.save(mMPITestCardFeature);
        return ResponseEntity
            .created(new URI("/api/mmpi-test-card-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmpi-test-card-features/:id} : Updates an existing mMPITestCardFeature.
     *
     * @param id the id of the mMPITestCardFeature to save.
     * @param mMPITestCardFeature the mMPITestCardFeature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPITestCardFeature,
     * or with status {@code 400 (Bad Request)} if the mMPITestCardFeature is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mMPITestCardFeature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MMPITestCardFeature> updateMMPITestCardFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPITestCardFeature mMPITestCardFeature
    ) throws URISyntaxException {
        log.debug("REST request to update MMPITestCardFeature : {}, {}", id, mMPITestCardFeature);
        if (mMPITestCardFeature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPITestCardFeature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPITestCardFeatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MMPITestCardFeature result = mMPITestCardFeatureService.update(mMPITestCardFeature);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPITestCardFeature.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmpi-test-card-features/:id} : Partial updates given fields of an existing mMPITestCardFeature, field will ignore if it is null
     *
     * @param id the id of the mMPITestCardFeature to save.
     * @param mMPITestCardFeature the mMPITestCardFeature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPITestCardFeature,
     * or with status {@code 400 (Bad Request)} if the mMPITestCardFeature is not valid,
     * or with status {@code 404 (Not Found)} if the mMPITestCardFeature is not found,
     * or with status {@code 500 (Internal Server Error)} if the mMPITestCardFeature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MMPITestCardFeature> partialUpdateMMPITestCardFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPITestCardFeature mMPITestCardFeature
    ) throws URISyntaxException {
        log.debug("REST request to partial update MMPITestCardFeature partially : {}, {}", id, mMPITestCardFeature);
        if (mMPITestCardFeature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPITestCardFeature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPITestCardFeatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MMPITestCardFeature> result = mMPITestCardFeatureService.partialUpdate(mMPITestCardFeature);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPITestCardFeature.getId().toString())
        );
    }

    /**
     * {@code GET  /mmpi-test-card-features} : get all the mMPITestCardFeatures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMPITestCardFeatures in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MMPITestCardFeature>> getAllMMPITestCardFeatures(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MMPITestCardFeatures");
        Page<MMPITestCardFeature> page = mMPITestCardFeatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mmpi-test-card-features/:id} : get the "id" mMPITestCardFeature.
     *
     * @param id the id of the mMPITestCardFeature to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mMPITestCardFeature, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MMPITestCardFeature> getMMPITestCardFeature(@PathVariable("id") Long id) {
        log.debug("REST request to get MMPITestCardFeature : {}", id);
        Optional<MMPITestCardFeature> mMPITestCardFeature = mMPITestCardFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMPITestCardFeature);
    }

    /**
     * {@code DELETE  /mmpi-test-card-features/:id} : delete the "id" mMPITestCardFeature.
     *
     * @param id the id of the mMPITestCardFeature to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMMPITestCardFeature(@PathVariable("id") Long id) {
        log.debug("REST request to delete MMPITestCardFeature : {}", id);
        mMPITestCardFeatureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
