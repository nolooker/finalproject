package com.party.repository;

import com.party.dto.MainProductDto;
import com.party.dto.ProductSearchDto;
import com.party.entity.Product;
import com.party.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    Page<MainProductDto> getMainProductPage(ProductSearchDto searchDto, Pageable pageable);

    List<ProductImage> findByProductIdOrderByIdAsc(Long productId);

    ProductImage findByProductIdAndRepImageYesNo(Long id, String repImageYesNo);

    Page<Product> getAdminProductPage(ProductSearchDto searchDto, Pageable pageable) ;

}
