package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.UserDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

@Service
public class UserServiceImpl extends AbstractServiceImpl implements UserService {
    public static final String ERROR_USER_EXISTS_BY_LOGIN = "User with login: {0} already exists";

    @Autowired
    UserDAO userDAO;

    public Integer insertUser(String login, String password) throws EntityCreatingException {
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.USER_LOGIN));
        CommonUtils.assertEmpty(password, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.USER_PASSWORD));

        if (isExistUserByLogin(login)) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_USER_EXISTS_BY_LOGIN, login));
            throw new EntityCreatingException(ex);
        }

        User user = createUser(login, password);
        validateEntity(user, (ex) -> {
            throw new EntityCreatingException(ex);
        });

        userDAO.insert(user);
        return user.getId();
    }

    public void updateUser(Integer id, String login, String password) throws EntityUpdatingException {
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.USER_LOGIN));
        CommonUtils.assertNull(id, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, Constants.ENTITY_ID));
        CommonUtils.assertNull(password, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, Constants.USER_PASSWORD));

        User user = findById(id);
        if (user == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND,
                    User.class.getCanonicalName(), id));
            throw new EntityUpdatingException(ex);
        }
        if (isExistUserByLogin(login)) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_USER_EXISTS_BY_LOGIN,
                    login));
            throw new EntityUpdatingException(ex);
        }

        user.setLogin(login);
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        validateEntity(user, (ex) -> {
            throw new EntityUpdatingException(ex);
        });

        userDAO.update(user);
    }

    public void deleteUser(Integer id) throws EntityDeletingException {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        User user = findById(id);
        if (user == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND,
                    User.class.getCanonicalName(), id));
            throw new EntityDeletingException(ex);
        }

        userDAO.delete(user);
    }

    public User findById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return userDAO.findById(id);
    }

    public User findByLogin(String login) {
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.USER_LOGIN));

        return userDAO.findByUniqueElement(login, User.LOGIN);
    }

    private boolean isExistUserByLogin(String login) {
        return findByLogin(login) != null;
    }

    private User createUser(String login, String password) {
        return new User(login, password);
    }
}
