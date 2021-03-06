package com.februy.shop.dao.product;

import com.februy.shop.common.domain.entity.product.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 5:39
 */
@Mapper
public interface ProductCategoryMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbggenerated
     */
    int insert(ProductCategory record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbggenerated
     */
    ProductCategory selectByPrimaryKey(Long id);
    ProductCategory selectSimpleByPrimaryKey(Long id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProductCategory record);

    List<ProductCategory> findAll();
    List<ProductCategory> findAllWithOutProducts();
    List<ProductCategory> findOnBoard();
}
