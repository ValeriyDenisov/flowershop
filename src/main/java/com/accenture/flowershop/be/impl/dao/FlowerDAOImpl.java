package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.entity.flower.Flower;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;

@Repository
public class FlowerDAOImpl extends AbstractDAOImpl<Flower> implements FlowerDAO {
    @Override
    protected Class<Flower> getType() {
        return Flower.class;
    }

    public Flower findByName(String name) {
        String query = MessageFormat.format(SELECT_BY_UNIQUE_ELEMENT_QUERY, getType().getSimpleName(), Flower.NAME);
        TypedQuery<Flower> selectQuery = entityManager.createQuery(query, getType());
        selectQuery.setParameter(Flower.NAME, name);
        try {
            return selectQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }
}
