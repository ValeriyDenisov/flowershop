package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.UserDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
    public static final String ERROR_USER_EXISTS_BY_LOGIN = "User with login: {0} already exists";

    @Autowired
    UserDAO userDAO;

    public Integer insertUser(String login, String password) {
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL_OR_EMPTY, Constants.USER_LOGIN));
        CommonUtils.assertEmpty(password, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL_OR_EMPTY, Constants.USER_PASSWORD));

        if(isExistUserByLogin(login)) {
            throw new EntityException(MessageFormat.format(ERROR_USER_EXISTS_BY_LOGIN, login));
        }

        User user = createUser(login, password);
        userDAO.insert(user);
        return user.getId();
    }

    public void updateUser(Integer id, String login, String password) {
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL_OR_EMPTY, Constants.USER_LOGIN));
        CommonUtils.assertNull(id, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL_OR_EMPTY, Constants.ENTITY_ID));
        CommonUtils.assertNull(id, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL_OR_EMPTY, Constants.USER_PASSWORD));

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
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL_OR_EMPTY, Constants.USER_LOGIN));

        return userDAO.findByUniqueElement(login, User.LOGIN);
    }

    private boolean isExistUserByLogin(String login) {
        return findByLogin(login) != null;
    }

    private User createUser(String login, String password) {
        return new User(login, password);
    }
}
