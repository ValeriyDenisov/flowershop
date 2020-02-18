package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.FlowerDTO;

import java.text.MessageFormat;

public class FlowerDTOConverter extends AbstractDTOConverter<Flower> {

    public static FlowerDTO convert(Flower flower) {
        CommonUtils.assertNull(flower, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_FLOWER));

        FlowerDTO flowerDTO = new FlowerDTO();
        flowerDTO.setName(flower.getName());
        flowerDTO.setPrice(flower.getPrice());
        flowerDTO.setQuantityInStock(flower.getQuantityInStock());
        flowerDTO.setId(flower.getId());
        return flowerDTO;
    }

    private FlowerDTOConverter() {
    }
}
