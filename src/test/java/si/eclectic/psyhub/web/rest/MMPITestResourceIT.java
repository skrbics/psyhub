package si.eclectic.psyhub.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
import si.eclectic.psyhub.domain.MMPITest;
import si.eclectic.psyhub.repository.MMPITestRepository;

/**
 * Integration tests for the {@link MMPITestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MMPITestResourceIT {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/mmpi-tests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MMPITestRepository mMPITestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMMPITestMockMvc;

    private MMPITest mMPITest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPITest createEntity(EntityManager em) {
        MMPITest mMPITest = new MMPITest().order(DEFAULT_ORDER).date(DEFAULT_DATE);
        return mMPITest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPITest createUpdatedEntity(EntityManager em) {
        MMPITest mMPITest = new MMPITest().order(UPDATED_ORDER).date(UPDATED_DATE);
        return mMPITest;
    }

    @BeforeEach
    public void initTest() {
        mMPITest = createEntity(em);
    }

    @Test
    @Transactional
    void createMMPITest() throws Exception {
        int databaseSizeBeforeCreate = mMPITestRepository.findAll().size();
        // Create the MMPITest
        restMMPITestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITest)))
            .andExpect(status().isCreated());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeCreate + 1);
        MMPITest testMMPITest = mMPITestList.get(mMPITestList.size() - 1);
        assertThat(testMMPITest.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testMMPITest.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createMMPITestWithExistingId() throws Exception {
        // Create the MMPITest with an existing ID
        mMPITest.setId(1L);

        int databaseSizeBeforeCreate = mMPITestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMPITestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITest)))
            .andExpect(status().isBadRequest());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMMPITests() throws Exception {
        // Initialize the database
        mMPITestRepository.saveAndFlush(mMPITest);

        // Get all the mMPITestList
        restMMPITestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMPITest.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getMMPITest() throws Exception {
        // Initialize the database
        mMPITestRepository.saveAndFlush(mMPITest);

        // Get the mMPITest
        restMMPITestMockMvc
            .perform(get(ENTITY_API_URL_ID, mMPITest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mMPITest.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMMPITest() throws Exception {
        // Get the mMPITest
        restMMPITestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMMPITest() throws Exception {
        // Initialize the database
        mMPITestRepository.saveAndFlush(mMPITest);

        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();

        // Update the mMPITest
        MMPITest updatedMMPITest = mMPITestRepository.findById(mMPITest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMMPITest are not directly saved in db
        em.detach(updatedMMPITest);
        updatedMMPITest.order(UPDATED_ORDER).date(UPDATED_DATE);

        restMMPITestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMMPITest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMMPITest))
            )
            .andExpect(status().isOk());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
        MMPITest testMMPITest = mMPITestList.get(mMPITestList.size() - 1);
        assertThat(testMMPITest.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testMMPITest.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMMPITest() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();
        mMPITest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPITestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mMPITest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPITest))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMMPITest() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();
        mMPITest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPITest))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMMPITest() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();
        mMPITest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMMPITestWithPatch() throws Exception {
        // Initialize the database
        mMPITestRepository.saveAndFlush(mMPITest);

        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();

        // Update the mMPITest using partial update
        MMPITest partialUpdatedMMPITest = new MMPITest();
        partialUpdatedMMPITest.setId(mMPITest.getId());

        partialUpdatedMMPITest.order(UPDATED_ORDER).date(UPDATED_DATE);

        restMMPITestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPITest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPITest))
            )
            .andExpect(status().isOk());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
        MMPITest testMMPITest = mMPITestList.get(mMPITestList.size() - 1);
        assertThat(testMMPITest.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testMMPITest.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMMPITestWithPatch() throws Exception {
        // Initialize the database
        mMPITestRepository.saveAndFlush(mMPITest);

        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();

        // Update the mMPITest using partial update
        MMPITest partialUpdatedMMPITest = new MMPITest();
        partialUpdatedMMPITest.setId(mMPITest.getId());

        partialUpdatedMMPITest.order(UPDATED_ORDER).date(UPDATED_DATE);

        restMMPITestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPITest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPITest))
            )
            .andExpect(status().isOk());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
        MMPITest testMMPITest = mMPITestList.get(mMPITestList.size() - 1);
        assertThat(testMMPITest.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testMMPITest.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMMPITest() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();
        mMPITest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPITestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mMPITest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITest))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMMPITest() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();
        mMPITest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITest))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMMPITest() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestRepository.findAll().size();
        mMPITest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mMPITest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPITest in the database
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMMPITest() throws Exception {
        // Initialize the database
        mMPITestRepository.saveAndFlush(mMPITest);

        int databaseSizeBeforeDelete = mMPITestRepository.findAll().size();

        // Delete the mMPITest
        restMMPITestMockMvc
            .perform(delete(ENTITY_API_URL_ID, mMPITest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MMPITest> mMPITestList = mMPITestRepository.findAll();
        assertThat(mMPITestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
