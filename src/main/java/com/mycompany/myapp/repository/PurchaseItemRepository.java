package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PurchaseItem;
import com.mycompany.myapp.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
    @Query(value = "SELECT * FROM purchase_item p WHERE p.is_finished = false AND  p.user_id = ?1", nativeQuery = true)
    List<PurchaseItem> findAllByFinishedIsTrueAndUserId(Long id);
}
