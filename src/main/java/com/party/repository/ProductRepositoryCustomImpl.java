package com.party.repository;

import com.party.constant.ProductStatus;
import com.party.dto.MainProductDto;
import com.party.dto.ProductSearchDto;
import com.party.dto.QMainProductDto;
import com.party.entity.Product;
import com.party.entity.ProductImage;
import com.party.entity.QProduct;
import com.party.entity.QProductImage;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private JPAQueryFactory queryFactory ;

    @Override
    public Page<MainProductDto> getMainProductPage(ProductSearchDto searchDto, Pageable pageable) {

        QProduct product = QProduct.product ;
        QProductImage productImage = QProductImage.productImage ;

        QueryResults<MainProductDto> results = this.queryFactory
                .select(
                        new QMainProductDto(
                                product.id,
                                product.name,
                                productImage.imageUrl,
                                product.price,
                                product.fit,
                                product.description

                        )
                )
                .from(productImage)
                .join(productImage.product, product)
                .where(productImage.repImageYesNo.eq("Y"))
                .where(likeCondition(searchDto.getSearchQuery()))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults() ;

        List<MainProductDto> content = results.getResults();
        long total = results.getTotal() ;

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<ProductImage> findByProductIdOrderByIdAsc(Long productId) {
        return null;
    }

    @Override
    public ProductImage findByProductIdAndRepImageYesNo(Long id, String repImageYesNo) {
        return null;
    }

    private BooleanExpression likeCondition(String searchQuery) {
        // 검색 키워드가 널이 아니면 like 연산을 수행합니다.
        return StringUtils.isEmpty(searchQuery) ? null : QProduct.product.name.like("%" + searchQuery + "%") ;
    }

    @Override
    public Page<Product> getAdminProductPage(ProductSearchDto searchDto, Pageable pageable) {
        QueryResults<Product> results = this.queryFactory
                .selectFrom(QProduct.product)
                .where(sellStatusCondition(searchDto.getProductStatus()),
                        searchByCondition(searchDto.getSearchBy(), searchDto.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByCondition(String searchBy, String searchQuery) {
        if(StringUtils.equals("name", searchBy)){ // 상품 이름으로 검색
            return QProduct.product.name.like("%" + searchQuery + "%" ) ;

        }else if(StringUtils.equals("createdBy", searchBy)){ // 상품 등록자로 검색
            return QProduct.product.createBy.like("%" + searchQuery + "%" ) ;
        }

        return null ;
    }

    private BooleanExpression sellStatusCondition(ProductStatus productStatus) {
        return productStatus == null ? null : QProduct.product.productStatus.eq(productStatus) ;
    }


}
