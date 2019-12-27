package com.februy.shop.service.pay;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 1:46
 */
public interface PayService {

    void deposit(Long userId, Integer amount);
    void setPaymentPassword(Long userId, String oldPaymentPassword, String newPaymentPassword);
    void decreaseAccount(Long userId, Double totalPrice, String paymentPassword);
    void increaseAccount(Long userId, Double totalPrice);
}
