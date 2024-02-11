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
import si.eclectic.psyhub.domain.Therapist;
import si.eclectic.psyhub.repository.TherapistRepository;

/**
 * Integration tests for the {@link TherapistResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TherapistResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/therapists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTherapistMockMvc;

    private Therapist therapist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Therapist createEntity(EntityManager em) {
        Therapist therapist = new Therapist()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .userid(DEFAULT_USERID);
        return therapist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Therapist createUpdatedEntity(EntityManager em) {
        Therapist therapist = new Therapist()
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userid(UPDATED_USERID);
        return therapist;
    }

    @BeforeEach
    public void initTest() {
        therapist = createEntity(em);
    }

    @Test
    @Transactional
    void createTherapist() throws Exception {
        int databaseSizeBeforeCreate = therapistRepository.findAll().size();
        // Create the Therapist
        restTherapistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(therapist)))
            .andExpect(status().isCreated());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeCreate + 1);
        Therapist testTherapist = therapistList.get(therapistList.size() - 1);
        assertThat(testTherapist.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTherapist.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testTherapist.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTherapist.getUserid()).isEqualTo(DEFAULT_USERID);
    }

    @Test
    @Transactional
    void createTherapistWithExistingId() throws Exception {
        // Create the Therapist with an existing ID
        therapist.setId(1L);

        int databaseSizeBeforeCreate = therapistRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTherapistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(therapist)))
            .andExpect(status().isBadRequest());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTherapists() throws Exception {
        // Initialize the database
        therapistRepository.saveAndFlush(therapist);

        // Get all the therapistList
        restTherapistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(therapist.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID)));
    }

    @Test
    @Transactional
    void getTherapist() throws Exception {
        // Initialize the database
        therapistRepository.saveAndFlush(therapist);

        // Get the therapist
        restTherapistMockMvc
            .perform(get(ENTITY_API_URL_ID, therapist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(therapist.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID));
    }

    @Test
    @Transactional
    void getNonExistingTherapist() throws Exception {
        // Get the therapist
        restTherapistMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTherapist() throws Exception {
        // Initialize the database
        therapistRepository.saveAndFlush(therapist);

        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();

        // Update the therapist
        Therapist updatedTherapist = therapistRepository.findById(therapist.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTherapist are not directly saved in db
        em.detach(updatedTherapist);
        updatedTherapist.firstName(UPDATED_FIRST_NAME).middleName(UPDATED_MIDDLE_NAME).lastName(UPDATED_LAST_NAME).userid(UPDATED_USERID);

        restTherapistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTherapist.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTherapist))
            )
            .andExpect(status().isOk());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
        Therapist testTherapist = therapistList.get(therapistList.size() - 1);
        assertThat(testTherapist.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTherapist.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testTherapist.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTherapist.getUserid()).isEqualTo(UPDATED_USERID);
    }

    @Test
    @Transactional
    void putNonExistingTherapist() throws Exception {
        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();
        therapist.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTherapistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, therapist.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(therapist))
            )
            .andExpect(status().isBadRequest());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTherapist() throws Exception {
        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();
        therapist.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTherapistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(therapist))
            )
            .andExpect(status().isBadRequest());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTherapist() throws Exception {
        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();
        therapist.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTherapistMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(therapist)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTherapistWithPatch() throws Exception {
        // Initialize the database
        therapistRepository.saveAndFlush(therapist);

        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();

        // Update the therapist using partial update
        Therapist partialUpdatedTherapist = new Therapist();
        partialUpdatedTherapist.setId(therapist.getId());

        partialUpdatedTherapist.lastName(UPDATED_LAST_NAME).userid(UPDATED_USERID);

        restTherapistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTherapist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTherapist))
            )
            .andExpect(status().isOk());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
        Therapist testTherapist = therapistList.get(therapistList.size() - 1);
        assertThat(testTherapist.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTherapist.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testTherapist.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTherapist.getUserid()).isEqualTo(UPDATED_USERID);
    }

    @Test
    @Transactional
    void fullUpdateTherapistWithPatch() throws Exception {
        // Initialize the database
        therapistRepository.saveAndFlush(therapist);

        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();

        // Update the therapist using partial update
        Therapist partialUpdatedTherapist = new Therapist();
        partialUpdatedTherapist.setId(therapist.getId());

        partialUpdatedTherapist
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userid(UPDATED_USERID);

        restTherapistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTherapist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTherapist))
            )
            .andExpect(status().isOk());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
        Therapist testTherapist = therapistList.get(therapistList.size() - 1);
        assertThat(testTherapist.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTherapist.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testTherapist.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTherapist.getUserid()).isEqualTo(UPDATED_USERID);
    }

    @Test
    @Transactional
    void patchNonExistingTherapist() throws Exception {
        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();
        therapist.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTherapistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, therapist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(therapist))
            )
            .andExpect(status().isBadRequest());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTherapist() throws Exception {
        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();
        therapist.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTherapistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(therapist))
            )
            .andExpect(status().isBadRequest());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTherapist() throws Exception {
        int databaseSizeBeforeUpdate = therapistRepository.findAll().size();
        therapist.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTherapistMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(therapist))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Therapist in the database
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTherapist() throws Exception {
        // Initialize the database
        therapistRepository.saveAndFlush(therapist);

        int databaseSizeBeforeDelete = therapistRepository.findAll().size();

        // Delete the therapist
        restTherapistMockMvc
            .perform(delete(ENTITY_API_URL_ID, therapist.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Therapist> therapistList = therapistRepository.findAll();
        assertThat(therapistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
