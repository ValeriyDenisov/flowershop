package com.accenture.flowershop.test.integration;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import org.apache.commons.collections.CollectionUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import static com.accenture.flowershop.be.impl.dao.AbstractDAOImpl.SELECT_ALL_QUERY;
import static com.accenture.flowershop.be.impl.dao.AbstractDAOImpl.SELECT_BY_UNIQUE_ELEMENT_QUERY;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/config/test-application-context.xml")
@Transactional
public abstract class AbstractIntegrationTest {
    @PersistenceContext
    protected EntityManager entityManager;

    public <T extends AbstractEntity> T findByUniqueElement(Object value, String entityName, Class<T> type, String elementName) {
        String query = MessageFormat.format(SELECT_BY_UNIQUE_ELEMENT_QUERY, entityName, elementName);
        TypedQuery<T> selectQuery = entityManager.createQuery(query, type);
        selectQuery.setParameter(elementName, value);
        try {
            return selectQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T extends AbstractEntity> List<T> findAll(Class<T> type) {
        String stringQuery = MessageFormat.format(SELECT_ALL_QUERY, type.getSimpleName());
        TypedQuery<T> query = entityManager.createQuery(stringQuery, type);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public <T extends AbstractEntity> T findById(Integer id, Class<T> type) {
        try {
            return entityManager.find(type, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
