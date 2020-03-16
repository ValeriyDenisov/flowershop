package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.entity.enums.Role;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractUser extends AbstractEntity {
    public static final String LOGIN = "login";

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "User role is null")
    protected Role role;

    @Column(name = "login", unique = true, nullable = false)
    @NotBlank(message = "User login is empty")
    @Size(min = 5, max = 50, message = "User login size is incorrect")
    @Email(message = "User login is incorrect")
    private String login;

    protected AbstractUser() {
    }


    protected AbstractUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Column(name = "password", nullable = false)
    @NotBlank(message = "User password is empty")
    @Size(min = 4, message = "User password is too short")
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("User{")
                .append("id=").append(id != null ? id : "")
                .append(", login=").append(login)
                .append(", password=").append(password)
                .append(", role=").append(role)
                .append("}");
        return builder.toString();
    }
}
