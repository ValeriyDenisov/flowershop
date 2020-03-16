package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.UserDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.be.entity.user.User_;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User, UserDAO> implements UserService {
    public static final String USER_CLASS_NAME = User.class.getSimpleName();
    public static final String ERROR_USER_EXISTS_BY_LOGIN = "User with login: {0} already exists";

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDAO userDAO;

    @Override
    public String getEntityName() {
        return USER_CLASS_NAME;
    }

    @Override
    public UserDAO getDAO() {
        return userDAO;
    }

    @Override
    public Integer insertUser(String login, String password) throws EntityCreatingException {
        return insertEntity(getUserMandatoryFields(login, password), () -> {
            if (isEntityExistsByFiled(User_.LOGIN, login)) {
                throwExceptionIfEntityAlreadyExistsByUniqueField(User_.LOGIN, login, (ex) -> {
                    throw new EntityCreatingException(ex);
                });
            }
            return createUser(login, password);
        });
    }

    @Override
    public void updateUser(Integer id, String login, String password) throws EntityUpdatingException {
        updateEntityByUniqueField(User_.ID, id, (user) -> updateUserFields(user, login, password));
    }

    @Override
    public void deleteUser(Integer id) throws EntityDeletingException {
        deleteEntityByUniqueField(User_.ID, id);
    }

    @Override
    public User findUserById(Integer id) {
        return findEntityByUniqueField(User_.ID, id);
    }

    @Override
    public User findUserByLogin(String login) {
        return findEntityByUniqueField(User_.LOGIN, login);
    }

    @Override
    public List<User> findAllUsers() {
        return findAllEntities();
    }

    private User createUser(String login, String password) {
        return new User(login, password);
    }

    private Map<String, Object> getUserMandatoryFields(String login, String password) {
        Map<String, Object> fieldsValues = new HashMap<>();

        fieldsValues.put(User_.LOGIN, login);
        fieldsValues.put(User_.PASSWORD, password);

        return fieldsValues;
    }

    private void updateUserFields(User user, String login, String password) {
        if (StringUtils.isNotEmpty(login) && !login.equalsIgnoreCase(user.getLogin())) {
            if (isEntityExistsByFiled(User_.LOGIN, login)) {
                throwExceptionIfEntityAlreadyExistsByUniqueField(User_.LOGIN, login, (ex) -> {
                    throw new EntityUpdatingException(ex);
                });
            }
        }
        user.setLogin(login);
        if (StringUtils.isNotEmpty(password)) {
            user.setPassword(password);
        }
    }
}
