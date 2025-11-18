package com.easyshop.catalog_service.exception;

import org.springframework.core.NestedRuntimeException;

public class ProductNotFoundException extends NestedRuntimeException {
    public ProductNotFoundException(String productCode) {
        super(String.format("Product with code %s not found", productCode));
    }
}
