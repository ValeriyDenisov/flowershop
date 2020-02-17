package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.UserDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
    public static final String ERROR_USER_LOGIN_EMPTY = "Поле login пользователя пусто";
    public static final String ERROR_USER_PASSWORD_EMPTY = "Поле password пользователя пусто";
    public static final String ERROR_USER_EXISTS_BY_LOGIN = "Пользователь с login:{0} уже существует";

    @Autowired
    UserDAO userDAO;

    public void insertUser(String login, String password) {
        CommonUtils.assertEmpty(login, ERROR_USER_LOGIN_EMPTY);
        CommonUtils.assertEmpty(password, ERROR_USER_PASSWORD_EMPTY);

        if(isExistUserByLogin(login)) {
            throw new EntityException(MessageFormat.format(ERROR_USER_EXISTS_BY_LOGIN, login));
        }

        User user = createUser(login, password);
        userDAO.insert(user);
    }

    public void updateUser(Integer id, String login, String password) {
        CommonUtils.assertEmpty(login, ERROR_USER_LOGIN_EMPTY);
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        User user = findById(id);
        if (user == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND,
                    User.class.getCanonicalName(), id));
        }
        if (isExistUserByLogin(login)) {
            throw new EntityException(MessageFormat.format(ERROR_USER_EXISTS_BY_LOGIN,
                    login));
        }

        user.setLogin(login);
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userDAO.update(user);
    }

    public void deleteUser(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        User user = findById(id);
        if (user == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND,
                    User.class.getCanonicalName(), id));
        }

        userDAO.delete(user);
    }

    public User findById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return userDAO.findById(id);
    }

    public User findByLogin(String login) {
        CommonUtils.assertEmpty(login, ERROR_USER_LOGIN_EMPTY);

        return userDAO.findByUniqueElement(login, User.LOGIN);
    }

    private boolean isExistUserByLogin(String login) {
        return findByLogin(login) != null;
    }

    private User createUser(String login, String password) {
        return new User(login, password);
    }
}
