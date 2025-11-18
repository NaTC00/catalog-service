package com.easyshop.catalog_service.middleware.db.repo;

import com.easyshop.catalog_service.middleware.db.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends R2dbcRepository<ProductEntity, Long> {
    Flux<ProductEntity> findAllBy(Pageable pageable);
}
