package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sale;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByUserId(Long id);
}
