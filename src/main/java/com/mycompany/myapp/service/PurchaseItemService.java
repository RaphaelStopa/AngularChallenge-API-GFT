package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseItemService {
    PurchaseItemDTO save(PurchaseItemDTO purchaseItemDTO);

    Optional<PurchaseItemDTO> partialUpdate(PurchaseItemDTO purchaseItemDTO);

    Page<PurchaseItemDTO> findAll(Pageable pageable);

    Optional<PurchaseItemDTO> findOne(Long id);

    void delete(Long id);

    List<PurchaseItemDTO> findAllByUser();
}
