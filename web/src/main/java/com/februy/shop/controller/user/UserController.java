package com.februy.shop.controller.user;

import com.februy.shop.common.domain.entity.user.User;
import com.februy.shop.service.user.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 7:41
 */
@RestController
@RequestMapping("/user/")
@Api(value = "user",description = "用户API")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "按某属性查询用户",notes = "属性可以是id或username或email或手机号",response = User.class,authorizations = {@Authorization("登陆权限")})
    @ApiResponses(value = {
            @ApiResponse(code = 401,message = "未登录"),
            @ApiResponse(code=404,message = "查询模式未找到"),
            @ApiResponse(code = 403,message = "只有管理员或用户自己能查询自己的用户信息")
    })
    @RequestMapping(value = "query/{key}",method = RequestMethod.GET)
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
        if(user==null) throw new UsernameNotFoundException(key);
        user.setPassword(null);
        return user;
    }
}
