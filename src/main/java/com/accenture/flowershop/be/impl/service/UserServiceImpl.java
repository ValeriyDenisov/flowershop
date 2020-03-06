package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.UserDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.functional.ThrowException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends AbstractServiceImpl implements UserService {
    public static final String ERROR_USER_EXISTS_BY_LOGIN = "User with login: {0} already exists";

    @Autowired
    UserDAO userDAO;

    @Override
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

        try {
            userDAO.insert(user);
        } catch (HibernateException e) {
            throw new EntityCreatingException(e);
        }
        return user.getId();
    }

    @Override
    public void updateUser(Integer id, String login, String password) throws EntityUpdatingException {
        CommonUtils.assertNull(id, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, Constants.ENTITY_ID));

        User user = findUserById(id);
        if (user == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND,
                    User.class.getCanonicalName(), id));
            throw new EntityUpdatingException(ex);
        }
        if (StringUtils.isNotEmpty(login) && !login.equalsIgnoreCase(user.getLogin())) {
            if (isExistUserByLogin(login)) {
                EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_USER_EXISTS_BY_LOGIN,
                        login));
                throw new EntityUpdatingException(ex);
            }
            user.setLogin(login);
        }
        if (StringUtils.isNotEmpty(password)) {
            user.setPassword(password);
        }
        validateEntity(user, (ex) -> {
            throw new EntityUpdatingException(ex);
        });

        try {
            userDAO.update(user);
        } catch (HibernateException e) {
            throw new EntityUpdatingException(e);
        }
    }

    @Override
    public void deleteUser(Integer id) throws EntityDeletingException {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        User user = findUserById(id);
        if (user == null) {
            EntityFindingException ex = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND,
                    User.class.getCanonicalName(), id));
            throw new EntityDeletingException(ex);
        }

        try {
            userDAO.delete(user);
        } catch (HibernateException e) {
            throw new EntityDeletingException(e);
        }
    }

    @Override
    public User findUserById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return userDAO.findById(id);
    }

    @Override
    public User findUserByLogin(String login) {
        CommonUtils.assertEmpty(login, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, Constants.USER_LOGIN));

        return userDAO.findByUniqueElement(login, User.LOGIN);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    private boolean isExistUserByLogin(String login) {
        return findUserByLogin(login) != null;
    }

    private User createUser(String login, String password) {
        return new User(login, password);
    }
}
