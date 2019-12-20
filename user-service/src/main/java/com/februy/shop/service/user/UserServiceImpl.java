package com.februy.shop.service.user;

import com.februy.shop.common.domain.entity.user.User;
import com.februy.shop.dao.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 8:18
 */
@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable("user")
    public User findById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("user")
    public User findByUsername(String userName) {
        return userMapper.findByUsername(userName);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("user")
    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("user")
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
