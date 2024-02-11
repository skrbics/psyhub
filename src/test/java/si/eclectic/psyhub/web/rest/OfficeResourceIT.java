package si.eclectic.psyhub.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.IntegrationTest;
import si.eclectic.psyhub.domain.Office;
import si.eclectic.psyhub.repository.OfficeRepository;

/**
 * Integration tests for the {@link OfficeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfficeResourceIT {

    private static final String DEFAULT_OFFICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/offices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfficeMockMvc;

    private Office office;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Office createEntity(EntityManager em) {
        Office office = new Office().officeName(DEFAULT_OFFICE_NAME).website(DEFAULT_WEBSITE).email(DEFAULT_EMAIL).phone(DEFAULT_PHONE);
        return office;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Office createUpdatedEntity(EntityManager em) {
        Office office = new Office().officeName(UPDATED_OFFICE_NAME).website(UPDATED_WEBSITE).email(UPDATED_EMAIL).phone(UPDATED_PHONE);
        return office;
    }

    @BeforeEach
    public void initTest() {
        office = createEntity(em);
    }

    @Test
    @Transactional
    void createOffice() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();
        // Create the Office
        restOfficeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isCreated());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate + 1);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(DEFAULT_OFFICE_NAME);
        assertThat(testOffice.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOffice.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOffice.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createOfficeWithExistingId() throws Exception {
        // Create the Office with an existing ID
        office.setId(1L);

        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOffices() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList
        restOfficeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeName").value(hasItem(DEFAULT_OFFICE_NAME)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get the office
        restOfficeMockMvc
            .perform(get(ENTITY_API_URL_ID, office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(office.getId().intValue()))
            .andExpect(jsonPath("$.officeName").value(DEFAULT_OFFICE_NAME))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingOffice() throws Exception {
        // Get the office
        restOfficeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office
        Office updatedOffice = officeRepository.findById(office.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOffice are not directly saved in db
        em.detach(updatedOffice);
        updatedOffice.officeName(UPDATED_OFFICE_NAME).website(UPDATED_WEBSITE).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOffice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOffice))
            )
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testOffice.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOffice.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOffice.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, office.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(office))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(office))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfficeWithPatch() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office using partial update
        Office partialUpdatedOffice = new Office();
        partialUpdatedOffice.setId(office.getId());

        partialUpdatedOffice.email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffice))
            )
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(DEFAULT_OFFICE_NAME);
        assertThat(testOffice.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOffice.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOffice.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateOfficeWithPatch() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office using partial update
        Office partialUpdatedOffice = new Office();
        partialUpdatedOffice.setId(office.getId());

        partialUpdatedOffice.officeName(UPDATED_OFFICE_NAME).website(UPDATED_WEBSITE).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffice))
            )
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testOffice.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOffice.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOffice.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, office.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(office))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(office))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeDelete = officeRepository.findAll().size();

        // Delete the office
        restOfficeMockMvc
            .perform(delete(ENTITY_API_URL_ID, office.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
