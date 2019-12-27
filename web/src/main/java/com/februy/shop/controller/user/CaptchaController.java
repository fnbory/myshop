package com.februy.shop.controller.user;

import com.februy.shop.common.base.domain.CaptchaDTO;
import com.februy.shop.common.base.domain.CaptchaVO;
import com.februy.shop.common.utils.CaptchaUtil;
import com.februy.shop.common.utils.UUIDUtil;
import com.februy.shop.commonweb.properties.AuthenticationProperties;
import com.februy.shop.commonweb.security.vertification.VerificationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 3:59
 */
@RestController
@RequestMapping("/captchas")
@Slf4j
public class CaptchaController {
    @Autowired
    private VerificationManager verificationManager;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @GetMapping
    public CaptchaVO getCaptcha() throws IOException{
        String uuid= UUIDUtil.uuid();
        CaptchaDTO captchaDTO = CaptchaUtil.createRandomCode();
        verificationManager.createVerificationCode(uuid,captchaDTO.getValue(),authenticationProperties.getActivationCodeExpireTime());
        log.info("uuid:{}", uuid);
        log.info("value:{}", captchaDTO);
        //返回图片
        return new CaptchaVO(captchaDTO.getImage(),uuid);
    }
}
