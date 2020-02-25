package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.FlowerDTO;

import java.text.MessageFormat;

public class FlowerDTOConverter extends AbstractDTOConverter<FlowerDTO, Flower> {

    @Override
    public FlowerDTO convert(Flower entity) {
        CommonUtils.assertNull(entity, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_FLOWER));

        FlowerDTO flowerDTO = new FlowerDTO();
        flowerDTO.setName(entity.getName());
        flowerDTO.setPrice(entity.getPrice());
        flowerDTO.setQuantityInStock(entity.getQuantityInStock());
        flowerDTO.setId(entity.getId());
        return flowerDTO;
    }

}
