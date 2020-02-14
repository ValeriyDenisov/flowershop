package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.AbstractDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.util.List;

@Transactional
public abstract class AbstractDAOImpl<T> implements AbstractDAO<T> {
    public static final String SELECT_ALL_QUERY = "From {0} a";
    public static final String SELECT_BY_UNIQUE_ELEMENT_QUERY = "SELECT E FROM {0} E WHERE {1} = :{1}";

    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<T> getType();

    public T findById(Integer id) {
        return entityManager.find(getType(), id);
    }

    public void insert(T object) {
        entityManager.persist(object);
    }

    public void delete(T object) {
        entityManager.detach(object);
    }

    public void update(T object) {
        entityManager.merge(object);
    }

    public List<T> findAll() {
        String query = MessageFormat.format(SELECT_ALL_QUERY, getType().getSimpleName());
        TypedQuery<T> selectQuery = entityManager.createQuery(query, getType());
        return selectQuery.getResultList();
    }
}
