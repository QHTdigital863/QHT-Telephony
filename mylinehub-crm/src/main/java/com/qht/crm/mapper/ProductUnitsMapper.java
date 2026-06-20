package com.qht.crm.mapper;

import com.qht.crm.entity.ProductUnits;
import com.qht.crm.entity.dto.ProductUnitsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductUnitsMapper {

    @Mapping(target = "unitOfMeasure", source = "unitOfMeasure.")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "organization", source = "organization")
    ProductUnitsDTO mapProductUnitsToDTO(ProductUnits productUnits);
}
