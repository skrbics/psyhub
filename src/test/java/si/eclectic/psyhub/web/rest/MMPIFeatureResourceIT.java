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
import si.eclectic.psyhub.domain.MMPIFeature;
import si.eclectic.psyhub.repository.MMPIFeatureRepository;

/**
 * Integration tests for the {@link MMPIFeatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MMPIFeatureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mmpi-features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MMPIFeatureRepository mMPIFeatureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMMPIFeatureMockMvc;

    private MMPIFeature mMPIFeature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPIFeature createEntity(EntityManager em) {
        MMPIFeature mMPIFeature = new MMPIFeature().name(DEFAULT_NAME);
        return mMPIFeature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPIFeature createUpdatedEntity(EntityManager em) {
        MMPIFeature mMPIFeature = new MMPIFeature().name(UPDATED_NAME);
        return mMPIFeature;
    }

    @BeforeEach
    public void initTest() {
        mMPIFeature = createEntity(em);
    }

    @Test
    @Transactional
    void createMMPIFeature() throws Exception {
        int databaseSizeBeforeCreate = mMPIFeatureRepository.findAll().size();
        // Create the MMPIFeature
        restMMPIFeatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPIFeature)))
            .andExpect(status().isCreated());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        MMPIFeature testMMPIFeature = mMPIFeatureList.get(mMPIFeatureList.size() - 1);
        assertThat(testMMPIFeature.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createMMPIFeatureWithExistingId() throws Exception {
        // Create the MMPIFeature with an existing ID
        mMPIFeature.setId(1L);

        int databaseSizeBeforeCreate = mMPIFeatureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMPIFeatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPIFeature)))
            .andExpect(status().isBadRequest());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMMPIFeatures() throws Exception {
        // Initialize the database
        mMPIFeatureRepository.saveAndFlush(mMPIFeature);

        // Get all the mMPIFeatureList
        restMMPIFeatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMPIFeature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getMMPIFeature() throws Exception {
        // Initialize the database
        mMPIFeatureRepository.saveAndFlush(mMPIFeature);

        // Get the mMPIFeature
        restMMPIFeatureMockMvc
            .perform(get(ENTITY_API_URL_ID, mMPIFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mMPIFeature.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMMPIFeature() throws Exception {
        // Get the mMPIFeature
        restMMPIFeatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMMPIFeature() throws Exception {
        // Initialize the database
        mMPIFeatureRepository.saveAndFlush(mMPIFeature);

        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();

        // Update the mMPIFeature
        MMPIFeature updatedMMPIFeature = mMPIFeatureRepository.findById(mMPIFeature.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMMPIFeature are not directly saved in db
        em.detach(updatedMMPIFeature);
        updatedMMPIFeature.name(UPDATED_NAME);

        restMMPIFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMMPIFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMMPIFeature))
            )
            .andExpect(status().isOk());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
        MMPIFeature testMMPIFeature = mMPIFeatureList.get(mMPIFeatureList.size() - 1);
        assertThat(testMMPIFeature.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMMPIFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();
        mMPIFeature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPIFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mMPIFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPIFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMMPIFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();
        mMPIFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPIFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMMPIFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();
        mMPIFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIFeatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPIFeature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMMPIFeatureWithPatch() throws Exception {
        // Initialize the database
        mMPIFeatureRepository.saveAndFlush(mMPIFeature);

        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();

        // Update the mMPIFeature using partial update
        MMPIFeature partialUpdatedMMPIFeature = new MMPIFeature();
        partialUpdatedMMPIFeature.setId(mMPIFeature.getId());

        restMMPIFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPIFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPIFeature))
            )
            .andExpect(status().isOk());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
        MMPIFeature testMMPIFeature = mMPIFeatureList.get(mMPIFeatureList.size() - 1);
        assertThat(testMMPIFeature.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMMPIFeatureWithPatch() throws Exception {
        // Initialize the database
        mMPIFeatureRepository.saveAndFlush(mMPIFeature);

        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();

        // Update the mMPIFeature using partial update
        MMPIFeature partialUpdatedMMPIFeature = new MMPIFeature();
        partialUpdatedMMPIFeature.setId(mMPIFeature.getId());

        partialUpdatedMMPIFeature.name(UPDATED_NAME);

        restMMPIFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPIFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPIFeature))
            )
            .andExpect(status().isOk());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
        MMPIFeature testMMPIFeature = mMPIFeatureList.get(mMPIFeatureList.size() - 1);
        assertThat(testMMPIFeature.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMMPIFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();
        mMPIFeature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPIFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mMPIFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPIFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMMPIFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();
        mMPIFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPIFeature))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMMPIFeature() throws Exception {
        int databaseSizeBeforeUpdate = mMPIFeatureRepository.findAll().size();
        mMPIFeature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mMPIFeature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPIFeature in the database
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMMPIFeature() throws Exception {
        // Initialize the database
        mMPIFeatureRepository.saveAndFlush(mMPIFeature);

        int databaseSizeBeforeDelete = mMPIFeatureRepository.findAll().size();

        // Delete the mMPIFeature
        restMMPIFeatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, mMPIFeature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MMPIFeature> mMPIFeatureList = mMPIFeatureRepository.findAll();
        assertThat(mMPIFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
