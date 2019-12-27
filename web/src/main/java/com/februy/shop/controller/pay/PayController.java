package com.februy.shop.controller.pay;

import com.februy.shop.common.base.exception.RestValidationException;
import com.februy.shop.common.domain.dto.pay.PaymentPasswordDTO;
import com.februy.shop.common.domain.dto.pay.PaymentPasswordModificationDTO;
import com.februy.shop.common.domain.entity.order.Order;
import com.februy.shop.commonweb.exception.security.AccessDeniedException;
import com.februy.shop.commonweb.security.domin.JWTUser;
import com.februy.shop.exception.order.OrderNotFoundException;
import com.februy.shop.exception.pay.DepositException;
import com.februy.shop.service.order.OrderService;
import com.februy.shop.service.pay.AccountService;
import com.februy.shop.service.pay.PayService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 8:48
 */
@RestController
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/users/{userId}/deposit")
    public void deposit( @PathVariable("userId") @ApiParam(value = "用户id", required = true) Long userId,
                         @RequestParam("amount") @ApiParam(value = "充值金额", required = true) Integer amount){
        if (amount <= 0) {
            throw new DepositException(String.valueOf(amount));
        }
        payService.deposit(userId, amount);
    }

    @PostMapping("/pay/{orderId}")
    public void pay(@PathVariable("orderId") Long orderId, @RequestBody PaymentPasswordDTO paymentPasswordDTO,
                    @AuthenticationPrincipal JWTUser user){
        Order order=orderService.findById(orderId);
        if(order==null) throw new OrderNotFoundException(String.valueOf(orderId));
        if(!user.getId().equals(order.getUser().getId())){
            throw new AccessDeniedException(user.getUsername());
        }
        accountService.commit(order,paymentPasswordDTO.getPaymentPassword());
    }

    @RequestMapping(value = "/users/{userId}/payment_password", method = RequestMethod.POST)
    public void setPaymentPassword(@PathVariable("userId") Long userId, @RequestBody @Valid PaymentPasswordModificationDTO dto, BindingResult result){
        if(result.hasErrors()) throw new RestValidationException(result.getFieldErrors());
        payService.setPaymentPassword(userId,dto.getOldPaymentPassword(),dto.getNewPaymentPassword());
    }
}
