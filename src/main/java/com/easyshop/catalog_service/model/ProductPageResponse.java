package com.easyshop.catalog_service.model;

import com.easyshop.catalog_service.generated.model.ProductResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPageResponse extends PageImpl<ProductResponse> {
    public ProductPageResponse(List<ProductResponse> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
