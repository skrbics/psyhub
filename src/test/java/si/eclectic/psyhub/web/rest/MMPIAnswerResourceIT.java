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
import si.eclectic.psyhub.domain.MMPIAnswer;
import si.eclectic.psyhub.repository.MMPIAnswerRepository;

/**
 * Integration tests for the {@link MMPIAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MMPIAnswerResourceIT {

    private static final Boolean DEFAULT_ANSWERED_YES = false;
    private static final Boolean UPDATED_ANSWERED_YES = true;

    private static final String ENTITY_API_URL = "/api/mmpi-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MMPIAnswerRepository mMPIAnswerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMMPIAnswerMockMvc;

    private MMPIAnswer mMPIAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPIAnswer createEntity(EntityManager em) {
        MMPIAnswer mMPIAnswer = new MMPIAnswer().answeredYes(DEFAULT_ANSWERED_YES);
        return mMPIAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPIAnswer createUpdatedEntity(EntityManager em) {
        MMPIAnswer mMPIAnswer = new MMPIAnswer().answeredYes(UPDATED_ANSWERED_YES);
        return mMPIAnswer;
    }

    @BeforeEach
    public void initTest() {
        mMPIAnswer = createEntity(em);
    }

    @Test
    @Transactional
    void createMMPIAnswer() throws Exception {
        int databaseSizeBeforeCreate = mMPIAnswerRepository.findAll().size();
        // Create the MMPIAnswer
        restMMPIAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPIAnswer)))
            .andExpect(status().isCreated());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        MMPIAnswer testMMPIAnswer = mMPIAnswerList.get(mMPIAnswerList.size() - 1);
        assertThat(testMMPIAnswer.getAnsweredYes()).isEqualTo(DEFAULT_ANSWERED_YES);
    }

    @Test
    @Transactional
    void createMMPIAnswerWithExistingId() throws Exception {
        // Create the MMPIAnswer with an existing ID
        mMPIAnswer.setId(1L);

        int databaseSizeBeforeCreate = mMPIAnswerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMPIAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPIAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMMPIAnswers() throws Exception {
        // Initialize the database
        mMPIAnswerRepository.saveAndFlush(mMPIAnswer);

        // Get all the mMPIAnswerList
        restMMPIAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMPIAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answeredYes").value(hasItem(DEFAULT_ANSWERED_YES.booleanValue())));
    }

    @Test
    @Transactional
    void getMMPIAnswer() throws Exception {
        // Initialize the database
        mMPIAnswerRepository.saveAndFlush(mMPIAnswer);

        // Get the mMPIAnswer
        restMMPIAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, mMPIAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mMPIAnswer.getId().intValue()))
            .andExpect(jsonPath("$.answeredYes").value(DEFAULT_ANSWERED_YES.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMMPIAnswer() throws Exception {
        // Get the mMPIAnswer
        restMMPIAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMMPIAnswer() throws Exception {
        // Initialize the database
        mMPIAnswerRepository.saveAndFlush(mMPIAnswer);

        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();

        // Update the mMPIAnswer
        MMPIAnswer updatedMMPIAnswer = mMPIAnswerRepository.findById(mMPIAnswer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMMPIAnswer are not directly saved in db
        em.detach(updatedMMPIAnswer);
        updatedMMPIAnswer.answeredYes(UPDATED_ANSWERED_YES);

        restMMPIAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMMPIAnswer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMMPIAnswer))
            )
            .andExpect(status().isOk());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
        MMPIAnswer testMMPIAnswer = mMPIAnswerList.get(mMPIAnswerList.size() - 1);
        assertThat(testMMPIAnswer.getAnsweredYes()).isEqualTo(UPDATED_ANSWERED_YES);
    }

    @Test
    @Transactional
    void putNonExistingMMPIAnswer() throws Exception {
        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();
        mMPIAnswer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPIAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mMPIAnswer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPIAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMMPIAnswer() throws Exception {
        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();
        mMPIAnswer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPIAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMMPIAnswer() throws Exception {
        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();
        mMPIAnswer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPIAnswer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMMPIAnswerWithPatch() throws Exception {
        // Initialize the database
        mMPIAnswerRepository.saveAndFlush(mMPIAnswer);

        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();

        // Update the mMPIAnswer using partial update
        MMPIAnswer partialUpdatedMMPIAnswer = new MMPIAnswer();
        partialUpdatedMMPIAnswer.setId(mMPIAnswer.getId());

        restMMPIAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPIAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPIAnswer))
            )
            .andExpect(status().isOk());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
        MMPIAnswer testMMPIAnswer = mMPIAnswerList.get(mMPIAnswerList.size() - 1);
        assertThat(testMMPIAnswer.getAnsweredYes()).isEqualTo(DEFAULT_ANSWERED_YES);
    }

    @Test
    @Transactional
    void fullUpdateMMPIAnswerWithPatch() throws Exception {
        // Initialize the database
        mMPIAnswerRepository.saveAndFlush(mMPIAnswer);

        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();

        // Update the mMPIAnswer using partial update
        MMPIAnswer partialUpdatedMMPIAnswer = new MMPIAnswer();
        partialUpdatedMMPIAnswer.setId(mMPIAnswer.getId());

        partialUpdatedMMPIAnswer.answeredYes(UPDATED_ANSWERED_YES);

        restMMPIAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPIAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPIAnswer))
            )
            .andExpect(status().isOk());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
        MMPIAnswer testMMPIAnswer = mMPIAnswerList.get(mMPIAnswerList.size() - 1);
        assertThat(testMMPIAnswer.getAnsweredYes()).isEqualTo(UPDATED_ANSWERED_YES);
    }

    @Test
    @Transactional
    void patchNonExistingMMPIAnswer() throws Exception {
        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();
        mMPIAnswer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPIAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mMPIAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPIAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMMPIAnswer() throws Exception {
        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();
        mMPIAnswer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPIAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMMPIAnswer() throws Exception {
        int databaseSizeBeforeUpdate = mMPIAnswerRepository.findAll().size();
        mMPIAnswer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPIAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mMPIAnswer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPIAnswer in the database
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMMPIAnswer() throws Exception {
        // Initialize the database
        mMPIAnswerRepository.saveAndFlush(mMPIAnswer);

        int databaseSizeBeforeDelete = mMPIAnswerRepository.findAll().size();

        // Delete the mMPIAnswer
        restMMPIAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, mMPIAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MMPIAnswer> mMPIAnswerList = mMPIAnswerRepository.findAll();
        assertThat(mMPIAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
