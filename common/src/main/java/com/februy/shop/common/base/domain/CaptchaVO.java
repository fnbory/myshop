package com.februy.shop.common.base.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 4:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVO {

    private String image;

    private String captchaCode;
}
