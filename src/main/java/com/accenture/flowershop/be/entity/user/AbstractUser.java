package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.entity.enums.Role;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractUser extends AbstractEntity {
    public static final String LOGIN = "login";

    @Column(name = "role", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    protected Role role;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    protected AbstractUser() {
    }


    protected AbstractUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Column(name = "password", nullable = false)
    private String password;

    public Role getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
