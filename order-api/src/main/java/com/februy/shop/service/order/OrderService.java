package com.februy.shop.service.order;

import com.februy.shop.common.domain.dto.order.OrderQueryConditionDTO;
import com.februy.shop.common.domain.entity.order.Order;
import com.github.pagehelper.PageInfo;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 8:58
 */
public interface OrderService {


    public com.februy.shop.common.domain.entity.order.Order placeOrder(Order order);

    PageInfo<Order> findAll(Integer pageNum, Integer pageSize);

    PageInfo<Order> findAllByCondition(OrderQueryConditionDTO queryDTO, Integer pageNum, Integer pageSize);
    Order findById(Long orderId);
    void updateOrder(Order order);
    void updateTimeOutOrders();
    void finishOrder(Order order);

}
