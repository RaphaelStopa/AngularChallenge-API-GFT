package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.PurchaseItem;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM product p WHERE p.quantity_stock > 0", nativeQuery = true)
    List<Product> findAllByQuantityStockGreaterThanZero();
}
