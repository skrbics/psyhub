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
import si.eclectic.psyhub.domain.MMPIFeature;
import si.eclectic.psyhub.repository.MMPIFeatureRepository;
import si.eclectic.psyhub.service.MMPIFeatureService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.MMPIFeature}.
 */
@RestController
@RequestMapping("/api/mmpi-features")
public class MMPIFeatureResource {

    private final Logger log = LoggerFactory.getLogger(MMPIFeatureResource.class);

    private static final String ENTITY_NAME = "mMPIFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MMPIFeatureService mMPIFeatureService;

    private final MMPIFeatureRepository mMPIFeatureRepository;

    public MMPIFeatureResource(MMPIFeatureService mMPIFeatureService, MMPIFeatureRepository mMPIFeatureRepository) {
        this.mMPIFeatureService = mMPIFeatureService;
        this.mMPIFeatureRepository = mMPIFeatureRepository;
    }

    /**
     * {@code POST  /mmpi-features} : Create a new mMPIFeature.
     *
     * @param mMPIFeature the mMPIFeature to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mMPIFeature, or with status {@code 400 (Bad Request)} if the mMPIFeature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MMPIFeature> createMMPIFeature(@RequestBody MMPIFeature mMPIFeature) throws URISyntaxException {
        log.debug("REST request to save MMPIFeature : {}", mMPIFeature);
        if (mMPIFeature.getId() != null) {
            throw new BadRequestAlertException("A new mMPIFeature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMPIFeature result = mMPIFeatureService.save(mMPIFeature);
        return ResponseEntity
            .created(new URI("/api/mmpi-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmpi-features/:id} : Updates an existing mMPIFeature.
     *
     * @param id the id of the mMPIFeature to save.
     * @param mMPIFeature the mMPIFeature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPIFeature,
     * or with status {@code 400 (Bad Request)} if the mMPIFeature is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mMPIFeature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MMPIFeature> updateMMPIFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPIFeature mMPIFeature
    ) throws URISyntaxException {
        log.debug("REST request to update MMPIFeature : {}, {}", id, mMPIFeature);
        if (mMPIFeature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPIFeature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPIFeatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MMPIFeature result = mMPIFeatureService.update(mMPIFeature);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPIFeature.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmpi-features/:id} : Partial updates given fields of an existing mMPIFeature, field will ignore if it is null
     *
     * @param id the id of the mMPIFeature to save.
     * @param mMPIFeature the mMPIFeature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPIFeature,
     * or with status {@code 400 (Bad Request)} if the mMPIFeature is not valid,
     * or with status {@code 404 (Not Found)} if the mMPIFeature is not found,
     * or with status {@code 500 (Internal Server Error)} if the mMPIFeature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MMPIFeature> partialUpdateMMPIFeature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPIFeature mMPIFeature
    ) throws URISyntaxException {
        log.debug("REST request to partial update MMPIFeature partially : {}, {}", id, mMPIFeature);
        if (mMPIFeature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPIFeature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPIFeatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MMPIFeature> result = mMPIFeatureService.partialUpdate(mMPIFeature);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPIFeature.getId().toString())
        );
    }

    /**
     * {@code GET  /mmpi-features} : get all the mMPIFeatures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMPIFeatures in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MMPIFeature>> getAllMMPIFeatures(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MMPIFeatures");
        Page<MMPIFeature> page = mMPIFeatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mmpi-features/:id} : get the "id" mMPIFeature.
     *
     * @param id the id of the mMPIFeature to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mMPIFeature, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MMPIFeature> getMMPIFeature(@PathVariable("id") Long id) {
        log.debug("REST request to get MMPIFeature : {}", id);
        Optional<MMPIFeature> mMPIFeature = mMPIFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMPIFeature);
    }

    /**
     * {@code DELETE  /mmpi-features/:id} : delete the "id" mMPIFeature.
     *
     * @param id the id of the mMPIFeature to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMMPIFeature(@PathVariable("id") Long id) {
        log.debug("REST request to delete MMPIFeature : {}", id);
        mMPIFeatureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
