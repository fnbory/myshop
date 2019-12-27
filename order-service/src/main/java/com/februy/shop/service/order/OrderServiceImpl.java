package com.februy.shop.service.order;

import com.februy.shop.common.domain.dto.order.OrderQueryConditionDTO;
import com.februy.shop.common.domain.entity.order.Order;
import com.februy.shop.common.enumeration.order.OrderStatus;
import com.februy.shop.dao.order.OrderMapper;
import com.februy.shop.properties.OrderProperties;
import com.februy.shop.service.product.ProductService;
import com.februy.shop.service.user.UserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 9:12
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public Order placeOrder(Order order) {
        orderMapper.insert(order);
        return order;
    }

    @Override
    public PageInfo<Order> findAll(Integer pageNum, Integer pageSize) {
        PageInfo<Order> page = orderMapper.findAll(pageNum, pageSize).toPageInfo();
        page.getList().forEach(
            order -> populateBean(order)
        );
        return page;
    }

    private void populateBean(Order order) {
        order.setUser(userService.findById(order.getUser().getId()));
        order.setProduct(productService.findProductById(order.getProduct().getId()));
    }

    @Override
    public PageInfo<Order> findAllByCondition(OrderQueryConditionDTO queryDTO, Integer pageNum, Integer pageSize) {
        if (queryDTO.getCategoryId() != null) {
            queryDTO.setProductIds(productService.findProductIdsByCategory(queryDTO.getCategoryId()));
        }
        PageInfo<Order> page = orderMapper.findByCondition(queryDTO, pageNum, pageSize).toPageInfo();
        page.getList().forEach(order -> populateBean(order));
        return page;
    }

    @Override
    public Order findById(Long orderId) {
        Order order=orderMapper.selectByPrimaryKey(orderId);
        populateBean(order);
        return  order;
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void updateTimeOutOrders() {
        findAllByCondition(OrderQueryConditionDTO.builder().status(OrderStatus.UNPAID).build(),0,0).getList().forEach(
                order -> {
                    log.info("未付款订单号:{}", order.getId());
                    if (Duration.between(order.getPlaceTime(), LocalDateTime.now()).toMinutes() >= OrderProperties.TIME_OUT_SPAN) {
                        order.setOrderStatus(OrderStatus.TIME_OUT);
                        orderMapper.updateByPrimaryKeySelective(order);
                        log.info("超时订单:{}", order.getId());
                    }
                }
        );
    }

    @Override
    public void finishOrder(Order order) {
        order.setOrderStatus(OrderStatus.PAID);
        this.updateOrder(order);
        // todo 企业转账

    }
}
