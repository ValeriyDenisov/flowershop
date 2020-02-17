package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.entity.user.User;

public interface UserService {
    void insertUser(String login, String password);

    void updateUser(Integer id, String login, String password);

    void deleteUser(Integer id);

    User findById(Integer id);

    User findByLogin(String login);

}
