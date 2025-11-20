package com.easyshop.catalog_service.model;

import com.easyshop.catalog_service.generated.model.ProductResponse;

public record PutProduct(ProductResponse productResponse, boolean newProduct) {
}
