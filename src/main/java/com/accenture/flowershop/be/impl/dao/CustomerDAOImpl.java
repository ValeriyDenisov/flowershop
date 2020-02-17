package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.entity.customer.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;

@Repository
public class CustomerDAOImpl extends AbstractDAOImpl<Customer> implements CustomerDAO {

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
