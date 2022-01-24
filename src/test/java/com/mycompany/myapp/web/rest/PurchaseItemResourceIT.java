package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PurchaseItem;
import com.mycompany.myapp.repository.PurchaseItemRepository;
import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import com.mycompany.myapp.service.mapper.PurchaseItemMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PurchaseItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurchaseItemResourceIT {

    private static final Boolean DEFAULT_IS_FINISHED = false;
    private static final Boolean UPDATED_IS_FINISHED = true;

    private static final Long DEFAULT_PRODUCT_QUANTITY = 1L;
    private static final Long UPDATED_PRODUCT_QUANTITY = 2L;

    private static final Double DEFAULT_UNITARY_VALUE = 1D;
    private static final Double UPDATED_UNITARY_VALUE = 2D;

    private static final Double DEFAULT_TOTAL_PRICE = 1D;
    private static final Double UPDATED_TOTAL_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/purchase-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private PurchaseItemMapper purchaseItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseItemMockMvc;

    private PurchaseItem purchaseItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseItem createEntity(EntityManager em) {
        PurchaseItem purchaseItem = new PurchaseItem()
            .isFinished(DEFAULT_IS_FINISHED)
            .productQuantity(DEFAULT_PRODUCT_QUANTITY)
            .unitaryValue(DEFAULT_UNITARY_VALUE)
            .totalPrice(DEFAULT_TOTAL_PRICE);
        return purchaseItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseItem createUpdatedEntity(EntityManager em) {
        PurchaseItem purchaseItem = new PurchaseItem()
            .isFinished(UPDATED_IS_FINISHED)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .unitaryValue(UPDATED_UNITARY_VALUE)
            .totalPrice(UPDATED_TOTAL_PRICE);
        return purchaseItem;
    }

    @BeforeEach
    public void initTest() {
        purchaseItem = createEntity(em);
    }

    @Test
    @Transactional
    void createPurchaseItem() throws Exception {
        int databaseSizeBeforeCreate = purchaseItemRepository.findAll().size();
        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);
        restPurchaseItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseItem testPurchaseItem = purchaseItemList.get(purchaseItemList.size() - 1);
        assertThat(testPurchaseItem.getIsFinished()).isEqualTo(DEFAULT_IS_FINISHED);
        assertThat(testPurchaseItem.getProductQuantity()).isEqualTo(DEFAULT_PRODUCT_QUANTITY);
        assertThat(testPurchaseItem.getUnitaryValue()).isEqualTo(DEFAULT_UNITARY_VALUE);
        assertThat(testPurchaseItem.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void createPurchaseItemWithExistingId() throws Exception {
        // Create the PurchaseItem with an existing ID
        purchaseItem.setId(1L);
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        int databaseSizeBeforeCreate = purchaseItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPurchaseItems() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        // Get all the purchaseItemList
        restPurchaseItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].isFinished").value(hasItem(DEFAULT_IS_FINISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].productQuantity").value(hasItem(DEFAULT_PRODUCT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitaryValue").value(hasItem(DEFAULT_UNITARY_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getPurchaseItem() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        // Get the purchaseItem
        restPurchaseItemMockMvc
            .perform(get(ENTITY_API_URL_ID, purchaseItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseItem.getId().intValue()))
            .andExpect(jsonPath("$.isFinished").value(DEFAULT_IS_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.productQuantity").value(DEFAULT_PRODUCT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unitaryValue").value(DEFAULT_UNITARY_VALUE.doubleValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPurchaseItem() throws Exception {
        // Get the purchaseItem
        restPurchaseItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPurchaseItem() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();

        // Update the purchaseItem
        PurchaseItem updatedPurchaseItem = purchaseItemRepository.findById(purchaseItem.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseItem are not directly saved in db
        em.detach(updatedPurchaseItem);
        updatedPurchaseItem
            .isFinished(UPDATED_IS_FINISHED)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .unitaryValue(UPDATED_UNITARY_VALUE)
            .totalPrice(UPDATED_TOTAL_PRICE);
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(updatedPurchaseItem);

        restPurchaseItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
        PurchaseItem testPurchaseItem = purchaseItemList.get(purchaseItemList.size() - 1);
        assertThat(testPurchaseItem.getIsFinished()).isEqualTo(UPDATED_IS_FINISHED);
        assertThat(testPurchaseItem.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testPurchaseItem.getUnitaryValue()).isEqualTo(UPDATED_UNITARY_VALUE);
        assertThat(testPurchaseItem.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();
        purchaseItem.setId(count.incrementAndGet());

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();
        purchaseItem.setId(count.incrementAndGet());

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();
        purchaseItem.setId(count.incrementAndGet());

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurchaseItemWithPatch() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();

        // Update the purchaseItem using partial update
        PurchaseItem partialUpdatedPurchaseItem = new PurchaseItem();
        partialUpdatedPurchaseItem.setId(purchaseItem.getId());

        partialUpdatedPurchaseItem.productQuantity(UPDATED_PRODUCT_QUANTITY).totalPrice(UPDATED_TOTAL_PRICE);

        restPurchaseItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseItem))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
        PurchaseItem testPurchaseItem = purchaseItemList.get(purchaseItemList.size() - 1);
        assertThat(testPurchaseItem.getIsFinished()).isEqualTo(DEFAULT_IS_FINISHED);
        assertThat(testPurchaseItem.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testPurchaseItem.getUnitaryValue()).isEqualTo(DEFAULT_UNITARY_VALUE);
        assertThat(testPurchaseItem.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void fullUpdatePurchaseItemWithPatch() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();

        // Update the purchaseItem using partial update
        PurchaseItem partialUpdatedPurchaseItem = new PurchaseItem();
        partialUpdatedPurchaseItem.setId(purchaseItem.getId());

        partialUpdatedPurchaseItem
            .isFinished(UPDATED_IS_FINISHED)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .unitaryValue(UPDATED_UNITARY_VALUE)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restPurchaseItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseItem))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
        PurchaseItem testPurchaseItem = purchaseItemList.get(purchaseItemList.size() - 1);
        assertThat(testPurchaseItem.getIsFinished()).isEqualTo(UPDATED_IS_FINISHED);
        assertThat(testPurchaseItem.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testPurchaseItem.getUnitaryValue()).isEqualTo(UPDATED_UNITARY_VALUE);
        assertThat(testPurchaseItem.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();
        purchaseItem.setId(count.incrementAndGet());

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purchaseItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();
        purchaseItem.setId(count.incrementAndGet());

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurchaseItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseItemRepository.findAll().size();
        purchaseItem.setId(count.incrementAndGet());

        // Create the PurchaseItem
        PurchaseItemDTO purchaseItemDTO = purchaseItemMapper.toDto(purchaseItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseItem in the database
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurchaseItem() throws Exception {
        // Initialize the database
        purchaseItemRepository.saveAndFlush(purchaseItem);

        int databaseSizeBeforeDelete = purchaseItemRepository.findAll().size();

        // Delete the purchaseItem
        restPurchaseItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, purchaseItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseItem> purchaseItemList = purchaseItemRepository.findAll();
        assertThat(purchaseItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
