package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractUser extends AbstractEntity {
    public static final String LOGIN = "login";

    @Transient
    protected String role;

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

    public String getRole() {
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
