package com.februy.shop.security;

import com.februy.shop.common.domain.entity.user.User;
import com.februy.shop.common.enumeration.user.UserStatus;
import com.februy.shop.commonweb.security.domin.JWTUser;
import com.februy.shop.exception.user.UserNotFoundException;
import com.februy.shop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 2:50
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.findByUsername(username);
        if(user==null) throw new UserNotFoundException(username);
        else if(user.getRoles()==null)  throw new UserNotFoundException(username);
        return new JWTUser(
                user.getId(),user.getUsername(),user.getPassword(),user.getUserStatus()!= UserStatus.UNACTIVATED,
                user.getUserStatus() != UserStatus.FORBIDDEN,true,true,
                user.getRoles().stream().map(
                        r->new SimpleGrantedAuthority(r.getRoleName().toUpperCase())
                ).collect(Collectors.toList())
        );
    }
}
