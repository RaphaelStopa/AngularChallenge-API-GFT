package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SaleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Sale}.
 */
public interface SaleService {
    /**
     * Save a sale.
     *
     * @param saleDTO the entity to save.
     * @return the persisted entity.
     */
    SaleDTO save(SaleDTO saleDTO);

    /**
     * Partially updates a sale.
     *
     * @param saleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaleDTO> partialUpdate(SaleDTO saleDTO);

    /**
     * Get all the sales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SaleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaleDTO> findOne(Long id);

    /**
     * Delete the "id" sale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
