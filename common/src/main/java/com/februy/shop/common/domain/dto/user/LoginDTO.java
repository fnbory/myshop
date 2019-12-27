package com.februy.shop.common.domain.dto.user;

import com.februy.shop.common.enumeration.user.UserMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 2:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {
    private String username;
    private String phone;
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String captchaCode;
    @NotNull
    private String captchaValue;
    @NotNull
    private UserMode userMode;
}

