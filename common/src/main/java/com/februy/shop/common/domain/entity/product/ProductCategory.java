package com.februy.shop.common.domain.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 5:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private String name;


    private String description;
    @JsonIgnore
    private Boolean isOnBoard;
    private String imageUrl;

    public ProductCategory(String name){
        this.name = name;
    }
}
