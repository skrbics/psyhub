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
import si.eclectic.psyhub.domain.Office;
import si.eclectic.psyhub.repository.OfficeRepository;
import si.eclectic.psyhub.service.OfficeService;
import si.eclectic.psyhub.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.psyhub.domain.Office}.
 */
@RestController
@RequestMapping("/api/offices")
public class OfficeResource {

    private final Logger log = LoggerFactory.getLogger(OfficeResource.class);

    private static final String ENTITY_NAME = "office";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeService officeService;

    private final OfficeRepository officeRepository;

    public OfficeResource(OfficeService officeService, OfficeRepository officeRepository) {
        this.officeService = officeService;
        this.officeRepository = officeRepository;
    }

    /**
     * {@code POST  /offices} : Create a new office.
     *
     * @param office the office to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new office, or with status {@code 400 (Bad Request)} if the office has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Office> createOffice(@RequestBody Office office) throws URISyntaxException {
        log.debug("REST request to save Office : {}", office);
        if (office.getId() != null) {
            throw new BadRequestAlertException("A new office cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Office result = officeService.save(office);
        return ResponseEntity
            .created(new URI("/api/offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offices/:id} : Updates an existing office.
     *
     * @param id the id of the office to save.
     * @param office the office to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated office,
     * or with status {@code 400 (Bad Request)} if the office is not valid,
     * or with status {@code 500 (Internal Server Error)} if the office couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@PathVariable(value = "id", required = false) final Long id, @RequestBody Office office)
        throws URISyntaxException {
        log.debug("REST request to update Office : {}, {}", id, office);
        if (office.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, office.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Office result = officeService.update(office);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, office.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /offices/:id} : Partial updates given fields of an existing office, field will ignore if it is null
     *
     * @param id the id of the office to save.
     * @param office the office to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated office,
     * or with status {@code 400 (Bad Request)} if the office is not valid,
     * or with status {@code 404 (Not Found)} if the office is not found,
     * or with status {@code 500 (Internal Server Error)} if the office couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Office> partialUpdateOffice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Office office
    ) throws URISyntaxException {
        log.debug("REST request to partial update Office partially : {}, {}", id, office);
        if (office.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, office.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Office> result = officeService.partialUpdate(office);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, office.getId().toString())
        );
    }

    /**
     * {@code GET  /offices} : get all the offices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Office>> getAllOffices(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Offices");
        Page<Office> page = officeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offices/:id} : get the "id" office.
     *
     * @param id the id of the office to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the office, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Office> getOffice(@PathVariable("id") Long id) {
        log.debug("REST request to get Office : {}", id);
        Optional<Office> office = officeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(office);
    }

    /**
     * {@code DELETE  /offices/:id} : delete the "id" office.
     *
     * @param id the id of the office to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable("id") Long id) {
        log.debug("REST request to delete Office : {}", id);
        officeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
