package com.easyshop.catalog_service.mapper;
import com.easyshop.catalog_service.generated.model.ProductRequest;
import com.easyshop.catalog_service.middleware.db.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductRequest productRequest);
}
