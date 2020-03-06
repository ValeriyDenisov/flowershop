package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.entity.flower.Flower;

import java.util.List;

public interface FlowerService {
    Flower findFlowerByName(String name);

    Flower findFlowerById(Integer id);

    List<Flower> findAllFlowers();

    Integer insertFlower(String name, Double price, Integer quantityInStock) throws EntityCreatingException;

    void updateFlower(Integer flowerId, String name, Double price, Integer quantityInStock) throws EntityUpdatingException;

    void deleteFlower(Integer flowerId) throws EntityDeletingException;

    List<Flower> findFlowersByParameters(String name, Double priceFrom, Double priceTo, Integer limit, Integer offset);
}
