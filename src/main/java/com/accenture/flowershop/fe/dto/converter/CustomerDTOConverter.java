package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class CustomerDTOConverter extends AbstractDTOConverter<CustomerDTO, Customer> {

    @Override
    public Class<CustomerDTO> getDTOType() {
        return CustomerDTO.class;
    }
}
