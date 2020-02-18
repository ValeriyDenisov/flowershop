package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.OrderDTO;

import java.text.MessageFormat;

public class OrderDTOConverter extends AbstractDTOConverter<Order> {

    public static OrderDTO convert(Order order) {
        CommonUtils.assertNull(order, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_ORDER));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setActive(order.getIsActive());
        orderDTO.setCloseDate(order.getCloseDate().toString());
        orderDTO.setOpenDate(order.getOpenDate().toString());
        orderDTO.setCustomer(CustomerDTOConverter.convert(order.getCustomer()));
        order.setPrice(orderDTO.getPrice());
        return orderDTO;
    }

    private OrderDTOConverter() {}
}
