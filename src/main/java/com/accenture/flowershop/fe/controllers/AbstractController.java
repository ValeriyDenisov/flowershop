package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;

import java.security.Principal;


public class AbstractController {
    public static final String ERROR = "Something wrong, please contact with administrator";
    public static final String REDIRECT = "redirect:";


    protected boolean isUser(Principal principal) {
        if (principal != null) {
            for (GrantedAuthority authority : ((UsernamePasswordAuthenticationToken) principal).getAuthorities()) {
                if (authority.getAuthority().equals(Constants.ROLE_USER)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isAdmin(Principal principal) {
        if (principal != null) {
            for (GrantedAuthority authority : ((UsernamePasswordAuthenticationToken) principal).getAuthorities()) {
                if (authority.getAuthority().equals(Constants.ROLE_ADMIN)) {
                    return true;
                }
            }
        }
        return false;
    }
}
