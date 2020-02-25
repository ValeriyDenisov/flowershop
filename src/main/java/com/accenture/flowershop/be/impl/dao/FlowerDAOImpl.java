package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.FlowerDAO;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FlowerDAOImpl extends AbstractDAOImpl<Flower> implements FlowerDAO {
    @Override
    protected Class<Flower> getType() {
        return Flower.class;
    }

    @Override
    public List<Flower> findByPrice(Double priceFrom, Double priceTo, Integer limit, Integer offset) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flower> criteriaQuery = criteriaBuilder.createQuery(getType());

        Root<Flower> flowerRoot = criteriaQuery.from(getType());
        List<Predicate> predicates = new ArrayList<>();

        if (priceFrom != null && priceTo != null) {
            Predicate priceBetweenCondition = criteriaBuilder.between(flowerRoot.get(Constants.FLOWER_PRICE), priceFrom, priceTo);
            predicates.add(priceBetweenCondition);
        } else {
            if (priceFrom != null) {
                Predicate priceFromCondition = criteriaBuilder.ge(flowerRoot.get(Constants.FLOWER_PRICE), priceFrom);
                predicates.add(priceFromCondition);
            } else {
                if (priceTo != null) {
                    Predicate priceToCondition = criteriaBuilder.le(flowerRoot.get(Constants.FLOWER_PRICE), priceTo);
                    predicates.add(priceToCondition);
                }
            }
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Flower> query = entityManager.createQuery(criteriaQuery);

        if (limit != null && limit != 0) {
            query.setFirstResult(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
