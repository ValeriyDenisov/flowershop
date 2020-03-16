package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.AbstractDAO;
import com.accenture.flowershop.be.api.exceptions.*;
import com.accenture.flowershop.be.api.functional.CreateEntity;
import com.accenture.flowershop.be.api.functional.TossException;
import com.accenture.flowershop.be.api.functional.UpdateEntity;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractServiceImpl<E extends AbstractEntity, D extends AbstractDAO<E>> {

    public final String ENTITY_INSERT_SUCCESS = "{0} successfully created and insert into database with id: {1}";
    public final String ENTITY_UPDATE_SUCCESS = "{0} with id: {1} successfully updated";
    public final String ENTITY_DELETE_SUCCESS = "{0} with id: {1} successfully deleted";
    public final String ENTITY_FIND_BY_UNIQUE_FIELD_SUCCESS = "{0} with {1}={2} successfully found";
    public final String ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE = "{0} with {1}={2} not found";
    public final String ENTITY_FIND_ALL_SUCCESS = "Object {0} was successfully found in quantity {1}";
    public final String ENTITY_FIND_ALL_FAILURE = "No one object {0} was found";
    public final String ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE_ALREADY_EXISTS = "{0} with {1}={2} already exists";
    public final String ENTITY_NOT_PERSISTENCE = "{0} not persistence";

    private Logger logger = LoggerFactory.getLogger(AbstractServiceImpl.class);

    @Autowired
    protected Validator validator;

    public static final String ERROR_ENTITY_ID_NULL = "Поле ID пусто!";
    public static final String ERROR_ENTITY_BY_ID_NOT_FOUND = "{0} с id:{1} не найден!";
    public static final String ERROR_ENTITY_BY_ID_EXISTS = "{0} с id:{1} уже существует!";

    public abstract String getEntityName();

    public abstract D getDAO();

    protected void validateEntity(E entity, TossException tossException) {
        Set<ConstraintViolation<E>> constraintViolations = validator.validate(entity);
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            String msg = CommonUtils.getViolationErrorsToString(constraintViolations);
            EntityViolationException e = new EntityViolationException(msg, constraintViolations);
            tossException.tossException(e);
        }
    }

    protected Integer insertEntity(Map<String, Object> mandatoryValues, CreateEntity<E> createEntity) {
        String assertViolations = CommonUtils.getAssertValuesViolations(mandatoryValues);
        if (StringUtils.isNotEmpty(assertViolations)) {
            logger.error(assertViolations);
            throw new EntityCreatingException(assertViolations);
        }

        E entity = createEntity.createEntity();

        validateEntity(entity, (ex -> {
            logger.error(ex.getMessage());
            throw new EntityCreatingException(ex);
        }));

        try {
            getDAO().insert(entity);
            logger.debug(MessageFormat.format(ENTITY_INSERT_SUCCESS, entity.toString(), entity.getId()));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw new EntityCreatingException(e);
        }
        return entity.getId();
    }

    protected void deleteEntityByUniqueField(String fieldName, Object fieldValue) {
        try {
            CommonUtils.assertEmptyOrNull(fieldValue, MessageFormat.format(CommonUtils.ERROR_ARGUMENT_NULL_OR_EMPTY, fieldName));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new EntityDeletingException(e);
        }

        E entity = findEntityByUniqueField(fieldName, fieldValue);
        assertEntityNull(entity, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE,
                getEntityName(), fieldName, fieldValue), (ex) -> {
            throw new EntityDeletingException(ex);
        });

        try {
            getDAO().delete(entity);
            logger.debug(MessageFormat.format(ENTITY_DELETE_SUCCESS, entity.toString(), entity.getId()));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw new EntityDeletingException(e);
        }
    }

    protected void updateEntityByUniqueField(String fieldName, Object fieldValue, UpdateEntity<E> updateEntityFields) {
        try {
            CommonUtils.assertEmpty(fieldName, MessageFormat.format(CommonUtils.ERROR_ARGUMENT_EMPTY, "fieldName"));
            CommonUtils.assertEmptyOrNull(fieldValue, MessageFormat.format(CommonUtils.ERROR_ARGUMENT_NULL_OR_EMPTY, "fieldValue"));
            CommonUtils.assertNull(updateEntityFields, MessageFormat.format(CommonUtils.ERROR_ARGUMENT_NULL, "updateEntityFields"));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new EntityUpdatingException(e);
        }

        E entity = findEntityByUniqueField(fieldName, fieldValue);
        assertEntityNull(entity, MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE,
                getEntityName(), fieldName, fieldValue), (ex) -> {
            throw new EntityUpdatingException(ex);
        });

        updateEntity(entity, updateEntityFields);
    }

    protected void updateEntityByUniqueField(E entity, UpdateEntity<E> updateEntityFields) {
        try {
            CommonUtils.assertNull(entity, MessageFormat.format(CommonUtils.ERROR_ARGUMENT_NULL_OR_EMPTY, "entity"));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new EntityUpdatingException(e);
        }

        if (isNotPersist(entity)) {
            throw new EntityUpdatingException(MessageFormat.format(ENTITY_NOT_PERSISTENCE, entity.toString()));
        }

        updateEntity(entity, updateEntityFields);
    }




    protected E findEntityByUniqueField(String fieldName, Object fieldValue) {
        try {
            CommonUtils.assertEmptyOrNull(fieldValue, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, fieldName));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new EntityFindingException(e.getMessage());
        }

        E entity = getDAO().findByUniqueElement(fieldValue, fieldName);
        if (entity != null) {
            logger.debug(MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_SUCCESS, getEntityName(), fieldName, fieldValue));
        }
        return entity;
    }


    protected List<E> findAllEntities() {
        List<E> entities = getDAO().findAll();

        if (CollectionUtils.isNotEmpty(entities)) {
            logger.debug(MessageFormat.format(ENTITY_FIND_ALL_SUCCESS, getEntityName(), entities.size()));
        } else {
            logger.error(MessageFormat.format(ENTITY_FIND_ALL_FAILURE, getEntityName()));
        }
        return entities;
    }

    protected boolean isEntityExistsByFiled(String filedName, Object fieldValue) {
        E entity = getDAO().findByUniqueElement(fieldValue, filedName);
        return entity != null;
    }

    protected <T extends AbstractEntity> void assertEntityNull(T entity, String message, TossException tossEntityFindingException) {
        if (entity == null) {
            EntityFindingException ex = new EntityFindingException(message);
            logger.error(message);
            tossEntityFindingException.tossException(ex);
        }
    }

    protected void throwExceptionIfEntityAlreadyExistsByUniqueField(String fieldName, String fieldValue, TossException tossEntityFindingException) {
        String message = MessageFormat.format(ENTITY_FIND_BY_UNIQUE_FIELD_FAILURE_ALREADY_EXISTS, getEntityName(), fieldName, fieldValue);
        EntityFindingException ex = new EntityFindingException(message);
        logger.error(message);
        tossEntityFindingException.tossException(ex);
    }

    protected <V extends AbstractEntity> boolean isPersist(V entity) {
        return getDAO().isPersist(entity);
    }

    protected <V extends AbstractEntity> boolean isNotPersist(V entity) {
        return !getDAO().isPersist(entity);
    }

    private void updateEntity(E entity, UpdateEntity<E> updateEntityFields) {
        updateEntityFields.updateEntity(entity);

        validateEntity(entity, (ex) -> {
            logger.error(ex.getMessage());
            throw new EntityUpdatingException(ex);
        });

        try {
            getDAO().update(entity);
            logger.debug(MessageFormat.format(ENTITY_UPDATE_SUCCESS, entity.toString(), entity.getId()));
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw new EntityUpdatingException(e);
        }
    }

}
