package com.februy.shop.controller.order;

import com.februy.shop.common.base.exception.RestValidationException;
import com.februy.shop.common.domain.dto.order.OrderQueryConditionDTO;
import com.februy.shop.common.domain.entity.order.Order;
import com.februy.shop.common.enumeration.order.OrderStatus;
import com.februy.shop.common.properties.PageProperties;
import com.februy.shop.commonweb.exception.security.AccessDeniedException;
import com.februy.shop.commonweb.security.domin.JWTUser;
import com.februy.shop.exception.order.OrderNotFoundException;
import com.februy.shop.exception.order.OrderStateIllegalException;
import com.februy.shop.exception.product.ProductNotFoundException;
import com.februy.shop.exception.user.UserNotFoundException;
import com.februy.shop.service.order.OrderService;
import com.februy.shop.service.product.ProductService;
import com.februy.shop.service.user.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 8:49
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Order placeOrder(@RequestBody @Valid Order order, BindingResult result){
        log.info("{}", order);
        if (result.hasErrors()) {
            throw new RestValidationException(result.getFieldErrors());
        }
        if(userService.findById(order.getUser().getId())==null){
            throw  new UserNotFoundException(String.valueOf(order.getUser().getId()));
        }
        else if(productService.findProductById(order.getProduct().getId())==null){
            throw new ProductNotFoundException(String.valueOf(order.getProduct().getId()));
        }
        order.setPlaceTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.UNPAID);
        return orderService.placeOrder(order);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageInfo<Order> findAll(@RequestParam(value = "pageNum", required = false, defaultValue = PageProperties.DEFAULT_PAGE_NUM)
                                     @ApiParam(value = "页码", required = false, defaultValue = PageProperties.DEFAULT_PAGE_NUM)
                                             Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = PageProperties.DEFAULT_PAGE_SIZE)
                                     @ApiParam(value = "页的大小", required = false, defaultValue = PageProperties.DEFAULT_PAGE_SIZE)
                                             Integer pageSize) {
        return orderService.findAll(pageNum, pageSize);
    }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    @ApiOperation(value = "分页按条件查询订单", response = Order.class, authorizations = {@Authorization("登录")})
    public PageInfo<Order> findAllByCondition(@RequestBody @Valid @ApiParam(value = "查询对象，包含了查询的各种条件", required = true) OrderQueryConditionDTO queryDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new RestValidationException(result.getFieldErrors());
        }
        if (queryDTO.getPageNum() == null || queryDTO.getPageNum() <= 0) {
            queryDTO.setPageNum(Integer.valueOf(PageProperties.DEFAULT_PAGE_NUM));
        }
        if (queryDTO.getPageSize() == null || queryDTO.getPageSize() <= 0) {
            queryDTO.setPageSize(Integer.valueOf(PageProperties.DEFAULT_PAGE_SIZE));
        }
        return orderService.findAllByCondition(queryDTO, queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "按id查询订单", response = Order.class, authorizations = {@Authorization("登录")})
    @PreAuthorize("hasRole('ADMIN')")
    public Order findById(@PathVariable("id") @ApiParam(value = "id", required = true) Long id) {
        return orderService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "更新订单", authorizations = {@Authorization("登录")})
    @PreAuthorize("hasRole('ADMIN') or principal.username == order.user.username")
    public void updateOrder(@RequestBody @Valid @ApiParam(value = "订单对象", required = true) Order order, BindingResult result) {
        if (result.hasErrors()) {
            throw new RestValidationException(result.getFieldErrors());
        }
        orderService.updateOrder(order);
    }

    @RequestMapping(value = "/cancel/{orderId}", method = RequestMethod.PUT)
    @ApiOperation(value = "取消订单", authorizations = {@Authorization("登录")})
    public void cancelOrder(@PathVariable @ApiParam(value = "订单id", required = true) Long orderId, @AuthenticationPrincipal JWTUser user) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(String.valueOf(orderId));
        }
        if (!user.getUsername().equals(order.getUser().getUsername())) {
            throw new AccessDeniedException(user.getUsername());
        }
        if (order.getOrderStatus() != OrderStatus.UNPAID) {
            throw new OrderStateIllegalException(order.getOrderStatus().toString());
        }
        order.setOrderStatus(OrderStatus.CANCELED);
        orderService.updateOrder(order);
    }

}
