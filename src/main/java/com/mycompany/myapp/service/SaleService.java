package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SaleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {
    SaleDTO save(SaleDTO saleDTO);

    Optional<SaleDTO> partialUpdate(SaleDTO saleDTO);

    Page<SaleDTO> findAll(Pageable pageable);

    Optional<SaleDTO> findOne(Long id);

    void delete(Long id);

    Double getTotal();
}
