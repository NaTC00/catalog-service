package com.easyshop.catalog_service.exception;

import org.springframework.core.NestedRuntimeException;

public class ProductAlreadyExistsException extends NestedRuntimeException {
    public ProductAlreadyExistsException(String productCode) {
        super("Product with code " + productCode + " already exists");
    }
}
