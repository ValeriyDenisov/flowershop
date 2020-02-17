package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.UserDAO;
import com.accenture.flowershop.be.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
    @Override
    protected Class<User> getType() {
        return User.class;
    }
}
