package com.februy.shop.common.domain.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 1:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPasswordDTO implements Serializable {
    private String paymentPassword;
}
