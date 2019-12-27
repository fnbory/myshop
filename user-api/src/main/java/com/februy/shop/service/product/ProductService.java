package com.februy.shop.service.product;

import com.februy.shop.common.domain.entity.product.Product;
import com.februy.shop.common.domain.entity.product.ProductCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 4:58
 */
public interface ProductService {

    ProductCategory findCategoryById(Long categoryId);

    List<ProductCategory> findAllCategories(Boolean containsProducts);

    List<ProductCategory> findCategoriesOnBoard();

    PageInfo<Product> findProductByCategory(Long categoryId, Integer pageNum, Integer pageSize);

    List<Product> findSimpleProductByCategory(Long categoryId);

    Product findProductById(Long id);

    List<Product> findProductsOnPromotion();

    void saveProduct(Product product);

    void updateProduct(Product product);

    void saveCategory(String name);

    void updateCategory(ProductCategory category);

    List<Long> findProductIdsByCategory(Long categoryId);
}
