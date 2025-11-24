package com.easyshop.catalog_service.api;

import com.easyshop.catalog_service.generated.model.ProductCategory;
import com.easyshop.catalog_service.generated.model.ProductResponse;
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
}
