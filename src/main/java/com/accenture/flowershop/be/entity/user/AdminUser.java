package com.accenture.flowershop.be.entity.user;


import com.accenture.flowershop.be.entity.enums.Role;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class AdminUser extends AbstractUser {
    public AdminUser() {
        super();
        this.role = Role.ADMIN;
    }

    public AdminUser(String login, String password) {
        super(login, password);
        this.role = Role.ADMIN;
    }

}
