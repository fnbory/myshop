package com.februy.shop.common.domain.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 1:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPasswordModificationDTO implements Serializable {
    private String oldPaymentPassword;
    @NotNull
    private String newPaymentPassword;
}