package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PurchaseItem}.
 */
public interface PurchaseItemService {
    /**
     * Save a purchaseItem.
     *
     * @param purchaseItemDTO the entity to save.
     * @return the persisted entity.
     */
    PurchaseItemDTO save(PurchaseItemDTO purchaseItemDTO);

    /**
     * Partially updates a purchaseItem.
     *
     * @param purchaseItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PurchaseItemDTO> partialUpdate(PurchaseItemDTO purchaseItemDTO);

    /**
     * Get all the purchaseItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" purchaseItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseItemDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
