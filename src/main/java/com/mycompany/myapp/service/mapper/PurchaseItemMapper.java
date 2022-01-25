package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PurchaseItem;
import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface PurchaseItemMapper extends EntityMapper<PurchaseItemDTO, PurchaseItem> {
    List<PurchaseItemDTO> toList(List<PurchaseItem> items);
}
