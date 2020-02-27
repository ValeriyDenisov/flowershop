package com.accenture.flowershop.be.api.dao;

import com.accenture.flowershop.be.entity.flower.Flower;

import java.util.List;

public interface FlowerDAO extends AbstractDAO<Flower> {
    List<Flower> findByParameters(String name, Double priceFrom, Double priceTo, Integer limit, Integer offset);
}
