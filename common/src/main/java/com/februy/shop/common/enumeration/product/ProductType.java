package com.februy.shop.common.enumeration.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 5:02
 */
@Getter
public enum ProductType {
    PRIMARY(0, "初级"), MIDDLE(1, "中级"), HIGH(2, "高级");

    private int code;

    private String desc;

    ProductType(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ProductType> map = new HashMap<>();

    static {
        for (ProductType status : values()) {
            map.put(status.code, status);
        }
    }

    public static ProductType getByCode(int code) {
        return map.get(code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
