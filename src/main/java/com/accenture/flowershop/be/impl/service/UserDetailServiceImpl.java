package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.AbstractUser;
import com.accenture.flowershop.be.entity.user.AdminUser;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl extends AbstractServiceImpl<User> implements UserDetailsService {
    private final String ADMIN_LOGIN = "admin";
    private final String ADMIN_PASSWORD = "admin123";

    @Autowired
    UserService userService;

    public UserDetails loadUserByUsername(String login) {
        AbstractUser user;
        Set<GrantedAuthority> roles;
        if (login.equalsIgnoreCase("admin")) {
            roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority("ADMIN"));
            user = new AdminUser(ADMIN_LOGIN, CommonUtils.getPasswordEncoder().encode(ADMIN_PASSWORD));
        } else {
            roles = new HashSet<GrantedAuthority>();
            roles.add(new SimpleGrantedAuthority("USER"));
            user = userService.findByLogin(login);
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                roles);
    }
}
