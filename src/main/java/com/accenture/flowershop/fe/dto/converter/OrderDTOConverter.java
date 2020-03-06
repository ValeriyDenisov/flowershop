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

    @Autowired
    CustomerDTOConverter customerDTOConverter;

    @Override
    public OrderDTO convert(Order entity) {
        CommonUtils.assertNull(entity, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_ORDER));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setActive(entity.getActive());
        if (entity.getCloseDate() != null) {
            orderDTO.setCloseDate(entity.getCloseDate().getTime().toString());
        }
        orderDTO.setOpenDate(entity.getOpenDate().getTime().toString());
        orderDTO.setPrice(entity.getPrice());
        orderDTO.setId(entity.getId());
        orderDTO.setCustomer(customerDTOConverter.convert(entity.getCustomer()));
        return orderDTO;
    }
}
