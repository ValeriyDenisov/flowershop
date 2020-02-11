package com.accenture.flowershop.be.impl.daoimpl;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.entity.user.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CustomerDAOImpl implements CustomerDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public Customer findById(int id) {
        return entityManager.find(Customer.class, id);
    }

}
