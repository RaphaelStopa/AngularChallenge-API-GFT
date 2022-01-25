package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PurchaseItemRepository;
import com.mycompany.myapp.service.PurchaseItemService;
import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PurchaseItem}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseItemResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseItemResource.class);

    private static final String ENTITY_NAME = "purchaseItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseItemService purchaseItemService;

    private final PurchaseItemRepository purchaseItemRepository;

    public PurchaseItemResource(PurchaseItemService purchaseItemService, PurchaseItemRepository purchaseItemRepository) {
        this.purchaseItemService = purchaseItemService;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    @PostMapping("/purchase-items")
    public ResponseEntity<PurchaseItemDTO> createPurchaseItem(@RequestBody PurchaseItemDTO purchaseItemDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseItem : {}", purchaseItemDTO);
        if (purchaseItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseItemDTO result = purchaseItemService.save(purchaseItemDTO);
        return ResponseEntity
            .created(new URI("/api/purchase-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/purchase-items/{id}")
    public ResponseEntity<PurchaseItemDTO> updatePurchaseItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PurchaseItemDTO purchaseItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PurchaseItem : {}, {}", id, purchaseItemDTO);
        if (purchaseItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchaseItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PurchaseItemDTO result = purchaseItemService.save(purchaseItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchaseItemDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/purchase-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PurchaseItemDTO> partialUpdatePurchaseItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PurchaseItemDTO purchaseItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PurchaseItem partially : {}, {}", id, purchaseItemDTO);
        if (purchaseItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchaseItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PurchaseItemDTO> result = purchaseItemService.partialUpdate(purchaseItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchaseItemDTO.getId().toString())
        );
    }

    @GetMapping("/purchase-items")
    public ResponseEntity<List<PurchaseItemDTO>> getAllPurchaseItems(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseItems");
        Page<PurchaseItemDTO> page = purchaseItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/purchase-items/{id}")
    public ResponseEntity<PurchaseItemDTO> getPurchaseItem(@PathVariable Long id) {
        log.debug("REST request to get PurchaseItem : {}", id);
        Optional<PurchaseItemDTO> purchaseItemDTO = purchaseItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseItemDTO);
    }

    @DeleteMapping("/purchase-items/{id}")
    public ResponseEntity<Void> deletePurchaseItem(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseItem : {}", id);
        purchaseItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
