package com.easyshop.catalog_service.middleware.db.repo;

import com.easyshop.catalog_service.middleware.db.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductRepository extends R2dbcRepository<ProductEntity, Long> {
}
