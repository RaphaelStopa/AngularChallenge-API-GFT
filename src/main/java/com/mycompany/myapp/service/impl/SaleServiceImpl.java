package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PurchaseItem;
import com.mycompany.myapp.domain.Sale;
import com.mycompany.myapp.repository.PurchaseItemRepository;
import com.mycompany.myapp.repository.SaleRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.SaleService;
import com.mycompany.myapp.service.dto.SaleDTO;
import com.mycompany.myapp.service.mapper.SaleMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sale}.
 */
@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    private final UserRepository userRepository;

    private final PurchaseItemRepository purchaseItemRepository;

    public SaleServiceImpl(
        SaleRepository saleRepository,
        SaleMapper saleMapper,
        UserRepository userRepository,
        PurchaseItemRepository purchaseItemRepository
    ) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.userRepository = userRepository;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    @Override
    public SaleDTO save(SaleDTO saleDTO) {
        log.debug("Request to save Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale.setDate(Instant.now());
        var user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).orElseThrow();
        sale.setUser(user);
        var listItems = purchaseItemRepository.findAllByFinishedIsTrueAndUserId(user.getId());
        sale = saleRepository.save(sale);
        for (PurchaseItem item : listItems) {
            item.setSale(sale);
            item.setFinished(true);
            purchaseItemRepository.save(item);
        }
        return saleMapper.toDto(sale);
    }

    @Override
    public Optional<SaleDTO> partialUpdate(SaleDTO saleDTO) {
        log.debug("Request to partially update Sale : {}", saleDTO);

        return saleRepository
            .findById(saleDTO.getId())
            .map(existingSale -> {
                saleMapper.partialUpdate(existingSale, saleDTO);

                return existingSale;
            })
            .map(saleRepository::save)
            .map(saleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sales");
        return saleRepository.findAll(pageable).map(saleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SaleDTO> findOne(Long id) {
        log.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id).map(saleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }

    @Override
    public Double getTotal() {
        var user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).orElseThrow();
        var listItems = purchaseItemRepository.findAllByFinishedIsTrueAndUserId(user.getId());

        Double value = 0.0;

        for (PurchaseItem item : listItems) {
            value = value + item.getTotalPrice();
            purchaseItemRepository.save(item);
        }
        return value;
    }
}
