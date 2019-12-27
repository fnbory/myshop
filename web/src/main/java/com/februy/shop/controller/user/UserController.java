package com.februy.shop.controller.user;

import com.februy.shop.common.base.exception.RestValidationException;
import com.februy.shop.common.domain.entity.user.User;
import com.februy.shop.common.enumeration.user.UserStatus;
import com.februy.shop.common.properties.PageProperties;
import com.februy.shop.common.utils.UUIDUtil;
import com.februy.shop.commonweb.properties.AuthenticationProperties;
import com.februy.shop.commonweb.security.vertification.VerificationManager;
import com.februy.shop.exception.ActivationCodeValidationException;
import com.februy.shop.exception.UserStatusInvalidException;
import com.februy.shop.exception.user.UserNotFoundException;
import com.februy.shop.exception.user.UsernameExistedException;
import com.februy.shop.service.email.EmailService;
import com.februy.shop.service.user.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 7:41
 */
@RestController
@RequestMapping("/users")
@Api(value = "user",description = "用户API")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationManager verificationManager;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Autowired
    private EmailService emailService;



    @ApiOperation(value = "按某属性查询用户",notes = "属性可以是id或username或email或手机号",response = User.class,authorizations = {@Authorization("登陆权限")})
    @ApiResponses(value = {
            @ApiResponse(code = 401,message = "未登录"),
            @ApiResponse(code=404,message = "查询模式未找到"),
            @ApiResponse(code = 403,message = "只有管理员或用户自己能查询自己的用户信息")
    })
    @RequestMapping(value = "/query/{key}",method = RequestMethod.GET)
    public User findByKey(@PathVariable("key") String key, @RequestParam("mode")String mode){
        User user=null;
        switch (mode){
            case "id":
                user=userService.findById(Long.parseLong(key));
                break;
            case "name":
                user=userService.findByUsername(key);
                break;
            case "phone":
                user=userService.findByPhone(key);
                break;
            case "email":
                user=userService.findByEmail(key);
            default:
                break;
        }
        if(user==null) throw new UserNotFoundException(key);
        user.setPassword(null);
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid  User user, /** 检验结果**/BindingResult result){
        log.info("{}",user);
        if(isUserNameDuplicated(user.getUsername())){
            throw new UsernameExistedException(user.getUsername());
        }
        else if(result.hasErrors()){
            throw new RestValidationException(result.getFieldErrors());
        }
        userService.save(user);
        return user;
    }

    @PostMapping(value = "/{id}/mail_validation")
    public void SendActivationEmail(@PathVariable("id") Long id){
        User user=userService.findById(id);
        if(user==null) throw new UserNotFoundException(String.valueOf(id));
        if(user.getUserStatus()!= UserStatus.UNACTIVATED){
            throw new UserStatusInvalidException(user.getUserStatus().toString());
        }
        String activateCode= UUIDUtil.uuid();
        verificationManager.createVerificationCode(activateCode,String.valueOf(id),authenticationProperties.getActivationCodeExpireTime());
        log.info("{}    {}" ,user.getEmail(),user.getId());
        Map<String,Object> params=new HashMap<>();
        params.put("id",user.getId());
        params.put("activateCode",activateCode);
        emailService.sendHTML(user.getEmail(),"activation",params,null);
    }

    @PostMapping(value = "/{id}/activation")
    public User activate(@PathVariable("id") Long id,@RequestParam("activationCode") String activationCode){
        User user=userService.findById(id);
        if(user==null) throw new  UserNotFoundException(String.valueOf(id));
        if(!verificationManager.checkVerificationCode(activationCode,String.valueOf(id))){
            verificationManager.deleteVerificationCode(activationCode);
            throw new ActivationCodeValidationException(activationCode);
        }
        user.setUserStatus(UserStatus.ACTIVATED);
        verificationManager.deleteVerificationCode(activationCode);
        userService.update(user);
        return user;
    }

    @GetMapping(value = "/{username}/dumplication")
    private boolean isUserNameDuplicated(@PathVariable("username") String username) {
        if(userService.findByUsername(username)==null){
            return false;
        }
        return true;
    }

    @PutMapping
    public void updateUser(@RequestBody @Valid User user,BindingResult result){
        if(result.hasErrors()){
            throw new RestValidationException(result.getFieldErrors());
        }
        userService.update(user);
    }

    @GetMapping
    public PageInfo<User> findAllUsers(@RequestParam(value ="pageNum" ,required = false,defaultValue = PageProperties.DEFAULT_PAGE_NUM) Integer pageNum,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = PageProperties.DEFAULT_PAGE_SIZE) Integer pageSize){
        return userService.findAll(pageNum,pageSize);
    }

    @GetMapping(value = "/query/fuzzy/{username}")
    public List<User> findUserIdAndNamesContaining(@PathVariable("username") String username) {
        return userService.findIdAndNameByUsernameContaining(username);
    }
}
