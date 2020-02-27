package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.OrderDAO;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class OrderDAOImpl extends AbstractDAOImpl<Order> implements OrderDAO {

    @Override
    @Transactional
    public List<Order> findByCustomerEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(getType());

        Root<Order> flowerRoot = criteriaQuery.from(getType());
        Join<Order, Customer> joinCustomer = flowerRoot.join(Constants.ORDER_CUSTOMER, JoinType.LEFT);
        Predicate predicate = criteriaBuilder.like(joinCustomer.get(Constants.CUSTOMER_EMAIL), "%" + email + "%");

        criteriaQuery.where(predicate);
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    protected Class<Order> getType() {
        return Order.class;
    }

}
