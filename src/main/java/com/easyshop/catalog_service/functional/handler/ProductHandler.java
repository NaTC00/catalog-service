package com.easyshop.catalog_service.functional.handler;

import com.easyshop.catalog_service.exception.ProductAlreadyExistsException;
import com.easyshop.catalog_service.generated.model.ProductRequest;
import com.easyshop.catalog_service.mapper.ProductMapper;
import com.easyshop.catalog_service.middleware.db.entity.ProductEntity;
import com.easyshop.catalog_service.middleware.db.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.dao.DuplicateKeyException;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandler {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Mono<ServerResponse> addProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductRequest.class)
                .flatMap(this::insertNewProduct)
                .map(entitySaved -> serverRequest.uri() + "/" + entitySaved.code())
                .flatMap(uriString -> ServerResponse.created(URI.create(uriString)).build());
    }

    private Mono<ProductEntity> insertNewProduct(ProductRequest productRequest) {
        final ProductEntity productEntity = productMapper.toEntity(productRequest);
        return productRepository.save(productEntity)
                .doOnNext(entitySaved -> log.debug("Product saved : {}", entitySaved))
                .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new ProductAlreadyExistsException(productEntity.code())));
    }
}
