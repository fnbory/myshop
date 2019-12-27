package com.februy.shop.service.product;

import com.februy.shop.common.domain.entity.product.Product;
import com.februy.shop.common.domain.entity.product.ProductCategory;
import com.februy.shop.dao.product.ProductCategoryMapper;
import com.februy.shop.dao.product.ProductMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 5:35
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Transactional(readOnly = true)
    @Override
    public ProductCategory findCategoryById(Long categoryId) {
        return productCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductCategory> findAllCategories(Boolean containsProducts) {
        if(containsProducts){
            return  productCategoryMapper.findAll();
        }
        else{
            return productCategoryMapper.findAllWithOutProducts();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductCategory> findCategoriesOnBoard() {
        return productCategoryMapper.findOnBoard();
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<Product> findProductByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        return  productMapper.findByCategoryPaging(categoryId,pageNum,pageSize).toPageInfo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findSimpleProductByCategory(Long categoryId) {
        return productMapper.findSimpleByCategory(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findProductById(Long productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProductsOnPromotion() {
        return productMapper.findOnPromotion();
    }

    @Transactional
    @Override
    public void saveProduct(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void saveCategory(String categoryName) {
        productCategoryMapper.insert(new ProductCategory(categoryName));
    }

    @Override
    public void updateCategory(ProductCategory category) {
        productCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findProductIdsByCategory(Long categoryId){
        return productMapper.findProductIdsByCategory(categoryId);
    }
}
