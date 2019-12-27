package com.februy.shop.controller.product;

import com.februy.shop.common.base.exception.RestValidationException;
import com.februy.shop.common.domain.entity.product.Product;
import com.februy.shop.common.domain.entity.product.ProductCategory;
import com.februy.shop.common.properties.PageProperties;
import com.februy.shop.service.product.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 4:55
 */

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/categories/{id}")
    public ProductCategory findCategoryById(@PathVariable("id") @ApiParam(value = "产品类别id", required = true) Long categoryId) {
        return productService.findCategoryById(categoryId);
    }

    @GetMapping(value = "/categories")
    public List<ProductCategory> findAllCategories(@RequestParam(value = "containsProducts", defaultValue = "false", required = false) Boolean containsProducts) {
        return productService.findAllCategories(containsProducts);
    }

    @GetMapping(value = "/categories/on_board")
    public List<ProductCategory> findCategoriesOnBoard() {
        return productService.findCategoriesOnBoard();
    }

    @RequestMapping(value = "/by_category/{categoryId}", method = RequestMethod.GET)
    public PageInfo<Product> findProductByCategory(@PathVariable("categoryId") Long categoryId,
                                                   @RequestParam(value = "pageNum", required = false, defaultValue = PageProperties.DEFAULT_PAGE_NUM)
                                                     @ApiParam(value = "页码", defaultValue = PageProperties.DEFAULT_PAGE_NUM)
                                                             Integer pageNum,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = PageProperties.DEFAULT_PAGE_SIZE)
                                                     @ApiParam(value = "页的大小", defaultValue = PageProperties.DEFAULT_PAGE_SIZE)
                                                             Integer pageSize) {
        return productService.findProductByCategory(categoryId, pageNum, pageSize);
    }

    @RequestMapping(value = "/by_category/{categoryId}/simple", method = RequestMethod.GET)
    public List<Product> findSimpleProductByCategory(@PathVariable("categoryId") Long categoryId) {
        return productService.findSimpleProductByCategory(categoryId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    @RequestMapping(value = "/on_promotion", method = RequestMethod.GET)
    public List<Product> findProductsOnPromotion() {
        return productService.findProductsOnPromotion();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void saveProduct(@RequestBody @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            throw new RestValidationException(result.getFieldErrors());
        }
        productService.saveProduct(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateProduct(@RequestBody @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            throw new RestValidationException(result.getFieldErrors());
        }
        productService.updateProduct(product);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void saveCategory(@RequestParam("name")  String name) {
        productService.saveCategory(name);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    @ApiOperation(value = "修改产品类别")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategory(@RequestBody @Valid @ApiParam(value = "产品类别", required = true) ProductCategory category, BindingResult result) {
        if (result.hasErrors()) {
            throw new RestValidationException(result.getFieldErrors());
        }
        productService.updateCategory(category);
    }

}
