package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PurchaseItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PurchaseItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {}
