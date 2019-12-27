package com.februy.shop.service.user;

import com.februy.shop.common.domain.entity.pay.Balance;
import com.februy.shop.common.domain.entity.user.User;
import com.februy.shop.common.enumeration.user.UserStatus;
import com.februy.shop.dao.pay.BalanceMapper;
import com.februy.shop.dao.user.RoleMapper;
import com.februy.shop.dao.user.UserMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 8:18
 */
@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder ;

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

    @Override
    @Transactional
    @CacheEvict(value = "User", allEntries = true)
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegTime(LocalDateTime.now());
        user.setUserStatus(UserStatus.UNACTIVATED);
        userMapper.insert(user);
        long roleId=roleMapper.findRoleIdByRoleName("ROLE_USER");
        roleMapper.insertUserRole(user.getId(),roleId);
        balanceMapper.insert(new Balance(user,0D,null));
    }

    @Override
    @Transactional
    @CacheEvict(value = "User", allEntries = true)
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional(readOnly = true)
    @CacheEvict(value = "UserDO", allEntries = true)
    @Override
    public PageInfo<User> findAll(Integer pageNum, Integer pageSize) {
        return userMapper.findAll(pageNum,pageSize).toPageInfo();
    }

    @Transactional(readOnly = true)
    @CacheEvict(value = "UserDO", allEntries = true)
    @Override
    public List<User> findIdAndNameByUsernameContaining(String username) {
        return userMapper.findIdAndNameByUsernameContaining(username);
    }
}
