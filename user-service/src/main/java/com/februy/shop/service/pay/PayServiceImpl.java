package com.februy.shop.service.pay;

import com.februy.shop.common.domain.entity.pay.Balance;
import com.februy.shop.dao.pay.BalanceMapper;
import com.februy.shop.exception.pay.BalanceNotEnoughException;
import com.februy.shop.exception.pay.PaymentPasswordInCorrectException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 2:43
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService{

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void deposit(Long userId, Integer amount) {
        Balance balance=balanceMapper.selectByPrimaryKey(userId);
        balance.setBalance(balance.getBalance()+amount);
        balanceMapper.updateByPrimaryKeySelective(balance);
    }

    @Transactional
    @Override
    public void setPaymentPassword(Long userId, String oldPaymentPassword, String newPaymentPassword) {
        Balance balance=balanceMapper.selectByPrimaryKey(userId);
        if (StringUtils.isNotEmpty(balance.getPaymentPassword()) && !bCryptPasswordEncoder.matches(oldPaymentPassword, balance.getPaymentPassword())) {
            throw new PaymentPasswordInCorrectException(balance.getUser().getId());
        }
        balance.setPaymentPassword(bCryptPasswordEncoder.encode(newPaymentPassword));
        balanceMapper.updateByPrimaryKeySelective(balance);
    }

    @Transactional
    @Override
    public void decreaseAccount(Long userId, Double totalPrice, String paymentPassword) {
        Balance balance=balanceMapper.selectByPrimaryKey(userId);
        if(balance==null) throw new BalanceNotEnoughException("0");
        if(totalPrice.compareTo(balance.getBalance())>0){
            log.info("{} 用户余额不足", userId);
            throw new BalanceNotEnoughException(String.valueOf(balance.getBalance()));
        }
        log.info("paymentPassword:{}", paymentPassword);
        log.info("{} matches passwordEncoder.matches(paymentPassword, balanceDO.getPaymentPassword())", paymentPassword, bCryptPasswordEncoder.matches(paymentPassword, balance.getPaymentPassword()));
        if (!bCryptPasswordEncoder.matches(paymentPassword, balance.getPaymentPassword())) {
            log.info("{} 用户支付密码错误", userId);
            throw new PaymentPasswordInCorrectException(userId);
        }
        balance.setBalance(balance.getBalance() - totalPrice);
        // 本地事务
        balanceMapper.updateByPrimaryKeySelective(balance);
    }

    @Transactional
    @Override
    public void increaseAccount(Long userId, Double totalPrice) {
        Balance balance=balanceMapper.selectByPrimaryKey(userId);
        balance.setBalance(balance.getBalance()+totalPrice);
        balanceMapper.updateByPrimaryKeySelective(balance);
    }
}
