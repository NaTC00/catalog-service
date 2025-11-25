package com.easyshop.catalog_service.api;

import com.easyshop.catalog_service.exception.ProductAlreadyExistsException;
import com.easyshop.catalog_service.exception.ProductNotFoundException;
import com.easyshop.catalog_service.generated.model.ProductCategory;
import com.easyshop.catalog_service.generated.model.ProductRequest;
import com.easyshop.catalog_service.generated.model.ProductResponse;
import com.easyshop.catalog_service.model.PutProduct;
import com.easyshop.catalog_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;


@WebFluxTest(ProductController.class)
public class ProductControllerTest {
    @MockBean
    private ProductService productService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void findProductByCodeOkTest() {
        var responseExpected = new ProductResponse()
                .code("code1")
                .category(ProductCategory.LAPTOP)
                .price(1000L);
        when(productService.findByCode("code1")).thenReturn(Mono.just(responseExpected));

        webTestClient.get()
                .uri("/products/code1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponse.class)
                .consumeWith(result -> assertThat(result.getResponseBody()).isEqualTo(responseExpected));

    }

    @Test
    public void findProductByCodeFailTest() {
        when(productService.findByCode("code1")).thenReturn(Mono.error(new ProductNotFoundException("code1")));

        webTestClient.get()
                .uri("/products/code1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void addProductOkTest() {
        var request = new ProductRequest()
                .code("code1")
                .category(ProductCategory.LAPTOP)
                .price(1000L);
        when(productService.addProduct(request)).thenReturn(Mono.just("code1"));
        webTestClient.post()
                .uri("/products")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Void.class)
                .consumeWith(result -> assertThat(result.getResponseHeaders().get("Location").get(0)).isEqualTo("/products/code1"));
    }

    @Test
    public void addProductFailTest() {
        var request = new ProductRequest()
                .code("code1")
                .category(ProductCategory.LAPTOP)
                .price(1000L);
        when(productService.addProduct(request)).thenReturn(Mono.error(new ProductAlreadyExistsException(request.getCode())));

        webTestClient.post()
                .uri("/products")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void addProductFailValidationTest() {
        var request = new ProductRequest()
                .category(ProductCategory.LAPTOP)
                .price(1000L);


        webTestClient.post()
                .uri("/products")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void editProductOk200Test(){
        var request = new ProductRequest()
                .code("code1")
                .category(ProductCategory.LAPTOP)
                .price(1000L);
        var responseExpected = new ProductResponse()
                .code("code1")
                .category(ProductCategory.LAPTOP)
                .price(1000L);
        when(productService.editByCode("code1", request)).thenReturn(Mono.just(new PutProduct(responseExpected, false)));

        webTestClient.put()
                .uri("/products/code1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponse.class)
                .consumeWith(result -> assertThat(result.getResponseBody()).isEqualTo(responseExpected));
    }
}
