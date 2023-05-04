package com.party.entity;

import com.party.constant.ProductStatus;
import com.party.dto.ProductFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "room")
@Getter@Setter@ToString
public class Product extends BaseEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,name = "price")
    private Integer price;

    @Column(nullable = false)
    private String fit;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    public void updateProduct(ProductFormDto productFormDto){
        this.name=productFormDto.getName();
        this.price=productFormDto.getPrice();
        this.fit=productFormDto.getFit();
        this.description= productFormDto.getDescription();
        this.productStatus=productFormDto.getProductStatus();
    }


}
