package com.februy.shop.service.user;

import com.februy.shop.common.domain.entity.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 7:39
 */
public interface UserService {
    User findById(long parseLong);

    User findByUsername(String key);

    User findByPhone(String key);

    User findByEmail(String key);

    void save(User user);

    void update(User user);

    PageInfo<User> findAll(Integer pageNum, Integer pageSize);

    List<User> findIdAndNameByUsernameContaining(String username);
}
