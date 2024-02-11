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
import si.eclectic.psyhub.domain.MMPIAnswer;
import si.eclectic.psyhub.repository.MMPIAnswerRepository;
import si.eclectic.psyhub.service.MMPIAnswerService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.MMPIAnswer}.
 */
@RestController
@RequestMapping("/api/mmpi-answers")
public class MMPIAnswerResource {

    private final Logger log = LoggerFactory.getLogger(MMPIAnswerResource.class);

    private static final String ENTITY_NAME = "mMPIAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MMPIAnswerService mMPIAnswerService;

    private final MMPIAnswerRepository mMPIAnswerRepository;

    public MMPIAnswerResource(MMPIAnswerService mMPIAnswerService, MMPIAnswerRepository mMPIAnswerRepository) {
        this.mMPIAnswerService = mMPIAnswerService;
        this.mMPIAnswerRepository = mMPIAnswerRepository;
    }

    /**
     * {@code POST  /mmpi-answers} : Create a new mMPIAnswer.
     *
     * @param mMPIAnswer the mMPIAnswer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mMPIAnswer, or with status {@code 400 (Bad Request)} if the mMPIAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MMPIAnswer> createMMPIAnswer(@RequestBody MMPIAnswer mMPIAnswer) throws URISyntaxException {
        log.debug("REST request to save MMPIAnswer : {}", mMPIAnswer);
        if (mMPIAnswer.getId() != null) {
            throw new BadRequestAlertException("A new mMPIAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMPIAnswer result = mMPIAnswerService.save(mMPIAnswer);
        return ResponseEntity
            .created(new URI("/api/mmpi-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mmpi-answers/:id} : Updates an existing mMPIAnswer.
     *
     * @param id the id of the mMPIAnswer to save.
     * @param mMPIAnswer the mMPIAnswer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPIAnswer,
     * or with status {@code 400 (Bad Request)} if the mMPIAnswer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mMPIAnswer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MMPIAnswer> updateMMPIAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPIAnswer mMPIAnswer
    ) throws URISyntaxException {
        log.debug("REST request to update MMPIAnswer : {}, {}", id, mMPIAnswer);
        if (mMPIAnswer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPIAnswer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPIAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MMPIAnswer result = mMPIAnswerService.update(mMPIAnswer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPIAnswer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mmpi-answers/:id} : Partial updates given fields of an existing mMPIAnswer, field will ignore if it is null
     *
     * @param id the id of the mMPIAnswer to save.
     * @param mMPIAnswer the mMPIAnswer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMPIAnswer,
     * or with status {@code 400 (Bad Request)} if the mMPIAnswer is not valid,
     * or with status {@code 404 (Not Found)} if the mMPIAnswer is not found,
     * or with status {@code 500 (Internal Server Error)} if the mMPIAnswer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MMPIAnswer> partialUpdateMMPIAnswer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MMPIAnswer mMPIAnswer
    ) throws URISyntaxException {
        log.debug("REST request to partial update MMPIAnswer partially : {}, {}", id, mMPIAnswer);
        if (mMPIAnswer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mMPIAnswer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mMPIAnswerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MMPIAnswer> result = mMPIAnswerService.partialUpdate(mMPIAnswer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMPIAnswer.getId().toString())
        );
    }

    /**
     * {@code GET  /mmpi-answers} : get all the mMPIAnswers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMPIAnswers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MMPIAnswer>> getAllMMPIAnswers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MMPIAnswers");
        Page<MMPIAnswer> page = mMPIAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mmpi-answers/:id} : get the "id" mMPIAnswer.
     *
     * @param id the id of the mMPIAnswer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mMPIAnswer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MMPIAnswer> getMMPIAnswer(@PathVariable("id") Long id) {
        log.debug("REST request to get MMPIAnswer : {}", id);
        Optional<MMPIAnswer> mMPIAnswer = mMPIAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMPIAnswer);
    }

    /**
     * {@code DELETE  /mmpi-answers/:id} : delete the "id" mMPIAnswer.
     *
     * @param id the id of the mMPIAnswer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMMPIAnswer(@PathVariable("id") Long id) {
        log.debug("REST request to delete MMPIAnswer : {}", id);
        mMPIAnswerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
