package com.party.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainProductDto {

    private Long id ;
    private String name ;
    private String imageUrl ;
    private Integer price ;
    private String fit ;
    private String description ;

    @QueryProjection
    public MainProductDto(Long id, String name, String imageUrl, Integer price, String fit, String description) {

        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.fit = fit;
        this.description = description;
    }

}
