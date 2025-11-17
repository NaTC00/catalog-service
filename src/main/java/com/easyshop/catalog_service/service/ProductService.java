package com.easyshop.catalog_service.service;

import com.easyshop.catalog_service.exception.ProductAlreadyExistsException;
import com.easyshop.catalog_service.generated.model.ProductRequest;
import com.easyshop.catalog_service.mapper.ProductMapper;
import com.easyshop.catalog_service.middleware.db.entity.ProductEntity;
import com.easyshop.catalog_service.middleware.db.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public Mono<String> addProduct(ProductRequest productRequest) {
        return insertProduct(productRequest)
                .map(ProductEntity::code);
    }

    private Mono<ProductEntity> insertProduct(ProductRequest productRequest) {
        var productEntity = productMapper.toEntity(productRequest);
        return productRepository.save(productEntity)
                .doOnNext(entitySaved -> log.info("Saved product : {}", entitySaved))
                .onErrorResume(DuplicateKeyException.class, e -> Mono.error(new ProductAlreadyExistsException(productEntity.code())));

    }

}
