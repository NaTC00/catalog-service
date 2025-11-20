package com.easyshop.catalog_service.functional.router;

import com.easyshop.catalog_service.functional.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> addProductRouter(ProductHandler productHandler) {
        return route(POST("/products/v2")
                .and(accept(MediaType.APPLICATION_JSON)), productHandler::addProduct);
    }

}
