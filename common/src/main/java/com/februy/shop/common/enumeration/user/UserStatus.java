package com.februy.shop.common.enumeration.user;

import com.februy.shop.common.domain.entity.user.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 7:24
 */
public enum  UserStatus {

    UNACTIVATED(0,"未激活"),ACTIVATED(1,"已激活"),FORBIDDEN(2,"已过期");

    private  int code;
    private String desc;

    private  static  Map<Integer, UserStatus> map=new HashMap<>();

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    static {
        for(UserStatus userStatus:values()){
            map.put(userStatus.code,userStatus);
        }
    }

    public static UserStatus getByCode(int code) {
        return map.get(code);
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
