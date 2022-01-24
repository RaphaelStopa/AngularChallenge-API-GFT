package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {}
