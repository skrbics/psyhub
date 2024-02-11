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
import si.eclectic.psyhub.domain.MMPITest;
import si.eclectic.psyhub.repository.MMPITestRepository;
import si.eclectic.psyhub.service.MMPITestService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.MMPITest}.
 */
@RestController
@RequestMapping("/api/mmpi-tests")
public class MMPITestResource {

    private final Logger log = LoggerFactory.getLogger(MMPITestResource.class);

    private static final String ENTITY_NAME = "mMPITest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MMPITestService mMPITestService;

    private final MMPITestRepository mMPITestRepository;

    public MMPITestResource(MMPITestService mMPITestService, MMPITestRepository mMPITestRepository) {
        this.mMPITestService = mMPITestService;
        this.mMPITestRepository = mMPITestRepository;
    }

    /**
     * {@code POST  /mmpi-tests} : Create a new mMPITest.
     *
     * @param mMPITest the mMPITest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mMPITest, or with status {@code 400 (Bad Request)} if the mMPITest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MMPITest> createMMPITest(@RequestBody MMPITest mMPITest) throws URISyntaxException {
        log.debug("REST request to save MMPITest : {}", mMPITest);
        if (mMPITest.getId() != null) {
            throw new BadRequestAlertException("A new mMPITest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMPITest result = mMPITestService.save(mMPITest);
        return ResponseEntity
            .created(new URI("/api/mmpi-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmpi-tests/:id} : Updates an existing mMPITest.
     *
     * @param id the id of the mMPITest to save.
     * @param mMPITest the mMPITest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPITest,
     * or with status {@code 400 (Bad Request)} if the mMPITest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mMPITest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MMPITest> updateMMPITest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPITest mMPITest
    ) throws URISyntaxException {
        log.debug("REST request to update MMPITest : {}, {}", id, mMPITest);
        if (mMPITest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPITest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPITestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MMPITest result = mMPITestService.update(mMPITest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPITest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmpi-tests/:id} : Partial updates given fields of an existing mMPITest, field will ignore if it is null
     *
     * @param id the id of the mMPITest to save.
     * @param mMPITest the mMPITest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPITest,
     * or with status {@code 400 (Bad Request)} if the mMPITest is not valid,
     * or with status {@code 404 (Not Found)} if the mMPITest is not found,
     * or with status {@code 500 (Internal Server Error)} if the mMPITest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MMPITest> partialUpdateMMPITest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPITest mMPITest
    ) throws URISyntaxException {
        log.debug("REST request to partial update MMPITest partially : {}, {}", id, mMPITest);
        if (mMPITest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPITest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPITestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MMPITest> result = mMPITestService.partialUpdate(mMPITest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPITest.getId().toString())
        );
    }

    /**
     * {@code GET  /mmpi-tests} : get all the mMPITests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMPITests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MMPITest>> getAllMMPITests(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MMPITests");
        Page<MMPITest> page = mMPITestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mmpi-tests/:id} : get the "id" mMPITest.
     *
     * @param id the id of the mMPITest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mMPITest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MMPITest> getMMPITest(@PathVariable("id") Long id) {
        log.debug("REST request to get MMPITest : {}", id);
        Optional<MMPITest> mMPITest = mMPITestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMPITest);
    }

    /**
     * {@code DELETE  /mmpi-tests/:id} : delete the "id" mMPITest.
     *
     * @param id the id of the mMPITest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMMPITest(@PathVariable("id") Long id) {
        log.debug("REST request to delete MMPITest : {}", id);
        mMPITestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
