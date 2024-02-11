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
import si.eclectic.psyhub.domain.MMPITestCard;
import si.eclectic.psyhub.repository.MMPITestCardRepository;

/**
 * Integration tests for the {@link MMPITestCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MMPITestCardResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mmpi-test-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MMPITestCardRepository mMPITestCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMMPITestCardMockMvc;

    private MMPITestCard mMPITestCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPITestCard createEntity(EntityManager em) {
        MMPITestCard mMPITestCard = new MMPITestCard().question(DEFAULT_QUESTION);
        return mMPITestCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MMPITestCard createUpdatedEntity(EntityManager em) {
        MMPITestCard mMPITestCard = new MMPITestCard().question(UPDATED_QUESTION);
        return mMPITestCard;
    }

    @BeforeEach
    public void initTest() {
        mMPITestCard = createEntity(em);
    }

    @Test
    @Transactional
    void createMMPITestCard() throws Exception {
        int databaseSizeBeforeCreate = mMPITestCardRepository.findAll().size();
        // Create the MMPITestCard
        restMMPITestCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITestCard)))
            .andExpect(status().isCreated());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeCreate + 1);
        MMPITestCard testMMPITestCard = mMPITestCardList.get(mMPITestCardList.size() - 1);
        assertThat(testMMPITestCard.getQuestion()).isEqualTo(DEFAULT_QUESTION);
    }

    @Test
    @Transactional
    void createMMPITestCardWithExistingId() throws Exception {
        // Create the MMPITestCard with an existing ID
        mMPITestCard.setId(1L);

        int databaseSizeBeforeCreate = mMPITestCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMMPITestCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITestCard)))
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMMPITestCards() throws Exception {
        // Initialize the database
        mMPITestCardRepository.saveAndFlush(mMPITestCard);

        // Get all the mMPITestCardList
        restMMPITestCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mMPITestCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)));
    }

    @Test
    @Transactional
    void getMMPITestCard() throws Exception {
        // Initialize the database
        mMPITestCardRepository.saveAndFlush(mMPITestCard);

        // Get the mMPITestCard
        restMMPITestCardMockMvc
            .perform(get(ENTITY_API_URL_ID, mMPITestCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mMPITestCard.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION));
    }

    @Test
    @Transactional
    void getNonExistingMMPITestCard() throws Exception {
        // Get the mMPITestCard
        restMMPITestCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMMPITestCard() throws Exception {
        // Initialize the database
        mMPITestCardRepository.saveAndFlush(mMPITestCard);

        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();

        // Update the mMPITestCard
        MMPITestCard updatedMMPITestCard = mMPITestCardRepository.findById(mMPITestCard.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMMPITestCard are not directly saved in db
        em.detach(updatedMMPITestCard);
        updatedMMPITestCard.question(UPDATED_QUESTION);

        restMMPITestCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMMPITestCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMMPITestCard))
            )
            .andExpect(status().isOk());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
        MMPITestCard testMMPITestCard = mMPITestCardList.get(mMPITestCardList.size() - 1);
        assertThat(testMMPITestCard.getQuestion()).isEqualTo(UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void putNonExistingMMPITestCard() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();
        mMPITestCard.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPITestCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mMPITestCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMMPITestCard() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();
        mMPITestCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMMPITestCard() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();
        mMPITestCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mMPITestCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMMPITestCardWithPatch() throws Exception {
        // Initialize the database
        mMPITestCardRepository.saveAndFlush(mMPITestCard);

        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();

        // Update the mMPITestCard using partial update
        MMPITestCard partialUpdatedMMPITestCard = new MMPITestCard();
        partialUpdatedMMPITestCard.setId(mMPITestCard.getId());

        partialUpdatedMMPITestCard.question(UPDATED_QUESTION);

        restMMPITestCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPITestCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPITestCard))
            )
            .andExpect(status().isOk());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
        MMPITestCard testMMPITestCard = mMPITestCardList.get(mMPITestCardList.size() - 1);
        assertThat(testMMPITestCard.getQuestion()).isEqualTo(UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void fullUpdateMMPITestCardWithPatch() throws Exception {
        // Initialize the database
        mMPITestCardRepository.saveAndFlush(mMPITestCard);

        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();

        // Update the mMPITestCard using partial update
        MMPITestCard partialUpdatedMMPITestCard = new MMPITestCard();
        partialUpdatedMMPITestCard.setId(mMPITestCard.getId());

        partialUpdatedMMPITestCard.question(UPDATED_QUESTION);

        restMMPITestCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMMPITestCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMMPITestCard))
            )
            .andExpect(status().isOk());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
        MMPITestCard testMMPITestCard = mMPITestCardList.get(mMPITestCardList.size() - 1);
        assertThat(testMMPITestCard.getQuestion()).isEqualTo(UPDATED_QUESTION);
    }

    @Test
    @Transactional
    void patchNonExistingMMPITestCard() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();
        mMPITestCard.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMMPITestCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mMPITestCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMMPITestCard() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();
        mMPITestCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mMPITestCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMMPITestCard() throws Exception {
        int databaseSizeBeforeUpdate = mMPITestCardRepository.findAll().size();
        mMPITestCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMMPITestCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mMPITestCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MMPITestCard in the database
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMMPITestCard() throws Exception {
        // Initialize the database
        mMPITestCardRepository.saveAndFlush(mMPITestCard);

        int databaseSizeBeforeDelete = mMPITestCardRepository.findAll().size();

        // Delete the mMPITestCard
        restMMPITestCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, mMPITestCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MMPITestCard> mMPITestCardList = mMPITestCardRepository.findAll();
        assertThat(mMPITestCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
