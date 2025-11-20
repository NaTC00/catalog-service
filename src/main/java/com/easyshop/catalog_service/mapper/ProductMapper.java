package com.easyshop.catalog_service.mapper;
import com.easyshop.catalog_service.generated.model.ProductPageResponse;
import com.easyshop.catalog_service.generated.model.ProductRequest;
import com.easyshop.catalog_service.generated.model.ProductResponse;
import com.easyshop.catalog_service.middleware.db.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductRequest productRequest);

    ProductEntity toEntity(ProductRequest productRequest, Long productId);

    ProductResponse toResponse(ProductEntity productEntity);

    List<ProductResponse> toProductResponse(List<ProductEntity> productEntities);

    ProductPageResponse toProductPageResponse(com.easyshop.catalog_service.model.ProductPageResponse productPageResponses);

}
