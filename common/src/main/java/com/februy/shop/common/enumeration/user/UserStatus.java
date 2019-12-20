package com.februy.shop.common.enumeration.user;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 7:24
 */
public enum  UserStatus {

    UNACTIVATED(0,"未激活"),ACTIVATED(1,"已激活"),FORBIDDEN(2,"已过期");

    private  int code;
    private String desc;

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
