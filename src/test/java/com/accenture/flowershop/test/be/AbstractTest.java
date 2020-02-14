package com.accenture.flowershop.test.be;

import com.accenture.flowershop.be.api.dao.AbstractDAO;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.impl.dao.AbstractDAOImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/config/test-application-context.xml")
@Transactional
public abstract class AbstractTest<T extends AbstractEntity> {
    protected static final String ERROR_TEST_OBJECT_NOT_FOUND = "Объект {0} не найден";
    protected static final String ERROR_TEST_INSERT_OBJECT = "Объект {0} не помещён в базу данных";
    protected static final String ERROR_TEST_WRONG_OBJECT = "Найден неправильный объект {0}";
    protected static final String ERROR_TEST_DELETE_OBJECT = "Объект {0} не удалён ";
    protected static final String ERROR_TEST_UPDATE_OBJECT = "Ошибка в обновлении объекта {0}";

    protected abstract String getTableName();

    protected abstract Class<T> getType();

    protected abstract void init();

    @PersistenceContext
    protected EntityManager entityManager;

    protected T findById(Integer id) {
        return entityManager.find(getType(), id);
    }

    protected void insert(T object) {
        entityManager.persist(object);
    }

    protected void delete(T object) {
        entityManager.detach(object);
    }

    protected T selectUnique(String query) {
        TypedQuery<T> selectQuery = entityManager.createQuery(query, getType());
        return selectQuery.getSingleResult();
    }


}
