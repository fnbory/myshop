package com.februy.shop.service.user;

import com.februy.shop.common.domain.entity.user.User;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 7:39
 */
public interface UserService {
    User findById(long parseLong);

    User findByUsername(String key);

    User findByPhone(String key);

    User findByEmail(String key);
}
