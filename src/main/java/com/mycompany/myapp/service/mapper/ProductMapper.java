package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.PurchaseItem;
import com.mycompany.myapp.service.dto.ProductDTO;
import com.mycompany.myapp.service.dto.PurchaseItemDTO;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    List<ProductDTO> toList(List<Product> items);
}
