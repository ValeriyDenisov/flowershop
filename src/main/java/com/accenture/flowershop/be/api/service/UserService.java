package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.entity.user.User;

import java.util.List;

public interface UserService {
    Integer insertUser(String login, String password) throws EntityCreatingException;

    void updateUser(Integer id, String login, String password) throws EntityUpdatingException;

    void deleteUser(Integer id) throws EntityDeletingException;

    User findUserById(Integer id);

    User findUserByLogin(String login);

    List<User> findAllUsers();

}
