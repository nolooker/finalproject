package com.party.repository;

import com.party.dto.ListProductDto;
import com.party.dto.ProductSearchDto;
import com.party.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminProductPage(ProductSearchDto searchDto, Pageable pageable);

    Page<ListProductDto> getMainProductPage(ProductSearchDto searchDto, Pageable pageable);
}



