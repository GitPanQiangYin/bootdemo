package com.boot.bootdemo.service;

import com.boot.bootdemo.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //UserInfo userInfo = new UserInfo();
        UserInfo userInfo=new UserInfo("admin", "123456", "ROLE_ADMIN", true,true,true, true);

        return userInfo;
    }
}
