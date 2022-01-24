package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PurchaseItem;
import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseItem} and its DTO {@link PurchaseItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseItemMapper extends EntityMapper<PurchaseItemDTO, PurchaseItem> {}
