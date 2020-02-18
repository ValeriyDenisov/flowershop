package com.accenture.flowershop.be.api.service;

import com.accenture.flowershop.be.entity.flower.Flower;

import java.util.List;

public interface FlowerService {
    Flower findFlowerByName(String name);

    Flower findFlowerById(Integer id);

    List<Flower> findAllFlower();

    Integer insertFlower(String name, Double price, Integer quantityInStock);

    void updateFlower(Integer flowerId, String name, Double price, Integer quantityInStock);

    void deleteFlower(Integer flowerId);


}
