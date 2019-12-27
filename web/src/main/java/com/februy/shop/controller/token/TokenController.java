package com.februy.shop.controller.token;

import com.februy.shop.common.base.exception.RestValidationException;
import com.februy.shop.common.domain.dto.user.LoginDTO;
import com.februy.shop.common.domain.dto.user.LoginSuccessResult;
import com.februy.shop.common.domain.entity.user.User;
import com.februy.shop.common.enumeration.user.UserStatus;
import com.februy.shop.commonweb.security.domin.JWTUser;
import com.februy.shop.commonweb.security.token.TokenManager;
import com.februy.shop.commonweb.security.vertification.VerificationManager;
import com.februy.shop.exception.CaptchaValidationException;
import com.februy.shop.exception.LoginInfoInvalidException;
import com.februy.shop.exception.UserStatusInvalidException;
import com.februy.shop.service.user.UserService;
import com.februy.shop.util.CosUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 2:10
 */
@RestController
@RequestMapping("/tokens")
@Slf4j
public class TokenController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private VerificationManager verificationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CosUtil cosUtil;

    @PostMapping
    public LoginSuccessResult login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result){
        log.info("{}",loginDTO);
        if(!verificationManager.checkVerificationCode(loginDTO.getCaptchaCode(),loginDTO.getCaptchaValue().toUpperCase())){
            verificationManager.deleteVerificationCode(loginDTO.getCaptchaCode());
            throw new CaptchaValidationException(loginDTO.getCaptchaValue());
        }
        if(result.hasErrors()){
            throw new RestValidationException(result.getFieldErrors());
        }
        String userMode=loginDTO.getUserMode().toString().toLowerCase();
        User user=null;
        switch (userMode){
            case "username":
                user=userService.findByUsername(loginDTO.getUsername());
                break;
            case "phone":
                user=userService.findByPhone(loginDTO.getPhone());
                break;
            case "email":
                user=userService.findByEmail(loginDTO.getEmail());
                break;
            default:
                break;
        }
        log.info("{}", user);
        String username = null;
        if (user != null) {
            username = user.getUsername();
        }
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,loginDTO.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (LockedException e) {
            throw new UserStatusInvalidException(UserStatus.FORBIDDEN.toString());
        } catch (DisabledException e) {
            throw new UserStatusInvalidException(UserStatus.UNACTIVATED.toString());
        } catch (AuthenticationException e) {
            throw new LoginInfoInvalidException(loginDTO);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 之前登陆过的话  清掉之前的token
        tokenManager.deleteToken(username);
        String token=tokenManager.createToken(username);
        verificationManager.deleteVerificationCode(loginDTO.getCaptchaCode());
        return new LoginSuccessResult(user.getId(),username,token);
    }

    @DeleteMapping
    public void logout(@AuthenticationPrincipal JWTUser user) { tokenManager.deleteToken(user.getUsername());}

//    @GetMapping("/cos")
//    public String   requestCosToken(@RequestParam("bucket") String bucket,@RequestParam("cosPath") String cosPath){
//        return cosUtil.getSign(bucket,cosPath);
//    }

}
