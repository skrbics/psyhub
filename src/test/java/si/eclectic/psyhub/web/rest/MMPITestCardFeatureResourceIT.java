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
import si.eclectic.psyhub.domain.MMPITestCardFeature;
import si.eclectic.psyhub.repository.MMPITestCardFeatureRepository;

/**
 * Integration tests for the {@link MMPITestCardFeatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MMPITestCardFeatureResourceIT {

    private static final Boolean DEFAULT_ANSWER_YES = false;
    private static final Boolean UPDATED_ANSWER_YES = true;

    private static final String ENTITY_API_URL = "/api/mmpi-test-card-features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MMPITestCardFeatureRepository mMPITestCardFeatureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMMPITestCardFeatureMockMvc;

    private MMPITestCardFeature mMPITestCardFeature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPITestCardFeature createEntity(EntityManager em) {
        MMPITestCardFeature mMPITestCardFeature = new MMPITestCardFeature().answerYes(DEFAULT_ANSWER_YES);
        return mMPITestCardFeature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPITestCardFeature createUpdatedEntity(EntityManager em) {
        MMPITestCardFeature mMPITestCardFeature = new MMPITestCardFeature().answerYes(UPDATED_ANSWER_YES);
        return mMPITestCardFeature;
    }

    @BeforeEach
    public void initTest() {
        mMPITestCardFeature = createEntity(em);
    }

    @Test
    @Transactional
    void createMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeCreate = mMPITestCardFeatureRepository.findAll().size();
        // Create the MMPITestCardFeature
        restMMPITestCardFeatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isCreated());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        MMPITestCardFeature testMMPITestCardFeature = mMPITestCardFeatureList.get(mMPITestCardFeatureList.size() - 1);
        assertThat(testMMPITestCardFeature.getAnswerYes()).isEqualTo(DEFAULT_ANSWER_YES);
    }

    @Test
    @Transactional
    void createMMPITestCardFeatureWithExistingId() throws Exception {
        // Create the MMPITestCardFeature with an existing ID
        mMPITestCardFeature.setId(1L);

        int databaseSizeBeforeCreate = mMPITestCardFeatureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMPITestCardFeatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMMPITestCardFeatures() throws Exception {
        // Initialize the database
        mMPITestCardFeatureRepository.saveAndFlush(mMPITestCardFeature);

        // Get all the mMPITestCardFeatureList
        restMMPITestCardFeatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMPITestCardFeature.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerYes").value(hasItem(DEFAULT_ANSWER_YES.booleanValue())));
    }

    @Test
    @Transactional
    void getMMPITestCardFeature() throws Exception {
        // Initialize the database
        mMPITestCardFeatureRepository.saveAndFlush(mMPITestCardFeature);

        // Get the mMPITestCardFeature
        restMMPITestCardFeatureMockMvc
            .perform(get(ENTITY_API_URL_ID, mMPITestCardFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mMPITestCardFeature.getId().intValue()))
            .andExpect(jsonPath("$.answerYes").value(DEFAULT_ANSWER_YES.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMMPITestCardFeature() throws Exception {
        // Get the mMPITestCardFeature
        restMMPITestCardFeatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMMPITestCardFeature() throws Exception {
        // Initialize the database
        mMPITestCardFeatureRepository.saveAndFlush(mMPITestCardFeature);

        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();

        // Update the mMPITestCardFeature
        MMPITestCardFeature updatedMMPITestCardFeature = mMPITestCardFeatureRepository.findById(mMPITestCardFeature.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMMPITestCardFeature are not directly saved in db
        em.detach(updatedMMPITestCardFeature);
        updatedMMPITestCardFeature.answerYes(UPDATED_ANSWER_YES);

        restMMPITestCardFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMMPITestCardFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMMPITestCardFeature))
            )
            .andExpect(status().isOk());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
        MMPITestCardFeature testMMPITestCardFeature = mMPITestCardFeatureList.get(mMPITestCardFeatureList.size() - 1);
        assertThat(testMMPITestCardFeature.getAnswerYes()).isEqualTo(UPDATED_ANSWER_YES);
    }

    @Test
    @Transactional
    void putNonExistingMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();
        mMPITestCardFeature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPITestCardFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mMPITestCardFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();
        mMPITestCardFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();
        mMPITestCardFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardFeatureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMMPITestCardFeatureWithPatch() throws Exception {
        // Initialize the database
        mMPITestCardFeatureRepository.saveAndFlush(mMPITestCardFeature);

        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();

        // Update the mMPITestCardFeature using partial update
        MMPITestCardFeature partialUpdatedMMPITestCardFeature = new MMPITestCardFeature();
        partialUpdatedMMPITestCardFeature.setId(mMPITestCardFeature.getId());

        restMMPITestCardFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPITestCardFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPITestCardFeature))
            )
            .andExpect(status().isOk());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
        MMPITestCardFeature testMMPITestCardFeature = mMPITestCardFeatureList.get(mMPITestCardFeatureList.size() - 1);
        assertThat(testMMPITestCardFeature.getAnswerYes()).isEqualTo(DEFAULT_ANSWER_YES);
    }

    @Test
    @Transactional
    void fullUpdateMMPITestCardFeatureWithPatch() throws Exception {
        // Initialize the database
        mMPITestCardFeatureRepository.saveAndFlush(mMPITestCardFeature);

        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();

        // Update the mMPITestCardFeature using partial update
        MMPITestCardFeature partialUpdatedMMPITestCardFeature = new MMPITestCardFeature();
        partialUpdatedMMPITestCardFeature.setId(mMPITestCardFeature.getId());

        partialUpdatedMMPITestCardFeature.answerYes(UPDATED_ANSWER_YES);

        restMMPITestCardFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPITestCardFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPITestCardFeature))
            )
            .andExpect(status().isOk());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
        MMPITestCardFeature testMMPITestCardFeature = mMPITestCardFeatureList.get(mMPITestCardFeatureList.size() - 1);
        assertThat(testMMPITestCardFeature.getAnswerYes()).isEqualTo(UPDATED_ANSWER_YES);
    }

    @Test
    @Transactional
    void patchNonExistingMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();
        mMPITestCardFeature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPITestCardFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mMPITestCardFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();
        mMPITestCardFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMMPITestCardFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardFeatureRepository.findAll().size();
        mMPITestCardFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCardFeature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPITestCardFeature in the database
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMMPITestCardFeature() throws Exception {
        // Initialize the database
        mMPITestCardFeatureRepository.saveAndFlush(mMPITestCardFeature);

        int databaseSizeBeforeDelete = mMPITestCardFeatureRepository.findAll().size();

        // Delete the mMPITestCardFeature
        restMMPITestCardFeatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, mMPITestCardFeature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MMPITestCardFeature> mMPITestCardFeatureList = mMPITestCardFeatureRepository.findAll();
        assertThat(mMPITestCardFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
