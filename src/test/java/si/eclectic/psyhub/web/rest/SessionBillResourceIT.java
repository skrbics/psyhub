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
import si.eclectic.psyhub.domain.SessionBill;
import si.eclectic.psyhub.repository.SessionBillRepository;

/**
 * Integration tests for the {@link SessionBillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SessionBillResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final String ENTITY_API_URL = "/api/session-bills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SessionBillRepository sessionBillRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionBillMockMvc;

    private SessionBill sessionBill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionBill createEntity(EntityManager em) {
        SessionBill sessionBill = new SessionBill().amount(DEFAULT_AMOUNT).paid(DEFAULT_PAID);
        return sessionBill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionBill createUpdatedEntity(EntityManager em) {
        SessionBill sessionBill = new SessionBill().amount(UPDATED_AMOUNT).paid(UPDATED_PAID);
        return sessionBill;
    }

    @BeforeEach
    public void initTest() {
        sessionBill = createEntity(em);
    }

    @Test
    @Transactional
    void createSessionBill() throws Exception {
        int databaseSizeBeforeCreate = sessionBillRepository.findAll().size();
        // Create the SessionBill
        restSessionBillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionBill)))
            .andExpect(status().isCreated());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeCreate + 1);
        SessionBill testSessionBill = sessionBillList.get(sessionBillList.size() - 1);
        assertThat(testSessionBill.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSessionBill.getPaid()).isEqualTo(DEFAULT_PAID);
    }

    @Test
    @Transactional
    void createSessionBillWithExistingId() throws Exception {
        // Create the SessionBill with an existing ID
        sessionBill.setId(1L);

        int databaseSizeBeforeCreate = sessionBillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionBillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionBill)))
            .andExpect(status().isBadRequest());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSessionBills() throws Exception {
        // Initialize the database
        sessionBillRepository.saveAndFlush(sessionBill);

        // Get all the sessionBillList
        restSessionBillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionBill.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())));
    }

    @Test
    @Transactional
    void getSessionBill() throws Exception {
        // Initialize the database
        sessionBillRepository.saveAndFlush(sessionBill);

        // Get the sessionBill
        restSessionBillMockMvc
            .perform(get(ENTITY_API_URL_ID, sessionBill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sessionBill.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSessionBill() throws Exception {
        // Get the sessionBill
        restSessionBillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSessionBill() throws Exception {
        // Initialize the database
        sessionBillRepository.saveAndFlush(sessionBill);

        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();

        // Update the sessionBill
        SessionBill updatedSessionBill = sessionBillRepository.findById(sessionBill.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSessionBill are not directly saved in db
        em.detach(updatedSessionBill);
        updatedSessionBill.amount(UPDATED_AMOUNT).paid(UPDATED_PAID);

        restSessionBillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSessionBill.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSessionBill))
            )
            .andExpect(status().isOk());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
        SessionBill testSessionBill = sessionBillList.get(sessionBillList.size() - 1);
        assertThat(testSessionBill.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSessionBill.getPaid()).isEqualTo(UPDATED_PAID);
    }

    @Test
    @Transactional
    void putNonExistingSessionBill() throws Exception {
        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();
        sessionBill.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionBillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionBill.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionBill))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSessionBill() throws Exception {
        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();
        sessionBill.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionBillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionBill))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSessionBill() throws Exception {
        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();
        sessionBill.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionBillMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sessionBill)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSessionBillWithPatch() throws Exception {
        // Initialize the database
        sessionBillRepository.saveAndFlush(sessionBill);

        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();

        // Update the sessionBill using partial update
        SessionBill partialUpdatedSessionBill = new SessionBill();
        partialUpdatedSessionBill.setId(sessionBill.getId());

        restSessionBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionBill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSessionBill))
            )
            .andExpect(status().isOk());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
        SessionBill testSessionBill = sessionBillList.get(sessionBillList.size() - 1);
        assertThat(testSessionBill.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSessionBill.getPaid()).isEqualTo(DEFAULT_PAID);
    }

    @Test
    @Transactional
    void fullUpdateSessionBillWithPatch() throws Exception {
        // Initialize the database
        sessionBillRepository.saveAndFlush(sessionBill);

        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();

        // Update the sessionBill using partial update
        SessionBill partialUpdatedSessionBill = new SessionBill();
        partialUpdatedSessionBill.setId(sessionBill.getId());

        partialUpdatedSessionBill.amount(UPDATED_AMOUNT).paid(UPDATED_PAID);

        restSessionBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionBill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSessionBill))
            )
            .andExpect(status().isOk());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
        SessionBill testSessionBill = sessionBillList.get(sessionBillList.size() - 1);
        assertThat(testSessionBill.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSessionBill.getPaid()).isEqualTo(UPDATED_PAID);
    }

    @Test
    @Transactional
    void patchNonExistingSessionBill() throws Exception {
        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();
        sessionBill.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sessionBill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionBill))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSessionBill() throws Exception {
        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();
        sessionBill.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionBill))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSessionBill() throws Exception {
        int databaseSizeBeforeUpdate = sessionBillRepository.findAll().size();
        sessionBill.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionBillMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sessionBill))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionBill in the database
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSessionBill() throws Exception {
        // Initialize the database
        sessionBillRepository.saveAndFlush(sessionBill);

        int databaseSizeBeforeDelete = sessionBillRepository.findAll().size();

        // Delete the sessionBill
        restSessionBillMockMvc
            .perform(delete(ENTITY_API_URL_ID, sessionBill.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SessionBill> sessionBillList = sessionBillRepository.findAll();
        assertThat(sessionBillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
