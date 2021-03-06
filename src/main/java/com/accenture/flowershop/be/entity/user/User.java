package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.enums.Role;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractUser {
    public User() {
        super();
        this.role = Role.USER;
    }

    public User(String login, String password) {
        super(login, password);
        this.role = Role.USER;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
