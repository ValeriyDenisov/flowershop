package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.FlowerDTO;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class FlowerDTOConverter extends AbstractDTOConverter<FlowerDTO, Flower> {

    @Override
    public Class<FlowerDTO> getDTOType() {
        return FlowerDTO.class;
    }
}
