package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.AbstractDAO;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.util.List;


public abstract class AbstractDAOImpl<T extends AbstractEntity> implements AbstractDAO<T> {
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
        entityManager.remove(object);
    }

    public void update(T object) {
        entityManager.merge(object);
    }

    @Override
    public <V extends AbstractEntity> boolean isPersist(V object) {
        return entityManager.contains(object);
    }

    public T findByUniqueElement(Object value, String elementName) {
        String query = MessageFormat.format(SELECT_BY_UNIQUE_ELEMENT_QUERY, getType().getSimpleName(), elementName);
        TypedQuery<T> selectQuery = entityManager.createQuery(query, getType());
        selectQuery.setParameter(elementName, value);
        try {
            return selectQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> findAll() {
        String query = MessageFormat.format(SELECT_ALL_QUERY, getType().getSimpleName());
        TypedQuery<T> selectQuery = entityManager.createQuery(query, getType());
        return selectQuery.getResultList();
    }


}
