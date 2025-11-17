package com.easyshop.catalog_service.api;

import com.easyshop.catalog_service.generated.api.ProductApi;
import com.easyshop.catalog_service.generated.model.ProductRequest;
import com.easyshop.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    public Mono<ResponseEntity<Void>> addProduct(Mono<ProductRequest> productRequest, ServerWebExchange exchange) {
        return productRequest
                .flatMap(productService::addProduct)
                .map(productCode -> ResponseEntity.created(URI.create(exchange.getRequest().getURI() + "/" + productCode)).build());
    }
}
