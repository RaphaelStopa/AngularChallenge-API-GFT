package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Sale;
import com.mycompany.myapp.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {}
