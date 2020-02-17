package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.impl.utils.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractUser {
    public User() {
        super();
        this.role = Constants.ROLE_USER;
    }

    public User(String login, String password) {
        super(login, password);
        this.role = Constants.ROLE_USER;
    }

}
