package com.februy.shop.common.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 2:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessResult implements Serializable {
    private Long id;
    private String username;
    private String token;
}