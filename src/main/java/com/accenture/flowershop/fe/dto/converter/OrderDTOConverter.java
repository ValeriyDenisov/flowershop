package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class OrderDTOConverter extends AbstractDTOConverter<OrderDTO, Order> {
    @Override
    public Class<OrderDTO> getDTOType() {
        return OrderDTO.class;
    }
}
