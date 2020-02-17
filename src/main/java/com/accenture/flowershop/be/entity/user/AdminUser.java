package com.accenture.flowershop.be.entity.user;


import com.accenture.flowershop.be.impl.utils.Constants;

public class AdminUser extends AbstractUser {
    public AdminUser() {
        super();
        this.role = Constants.ROLE_ADMIN;
    }

    public AdminUser(String login, String password) {
        super(login, password);
        this.role = Constants.ROLE_ADMIN;
    }

}
