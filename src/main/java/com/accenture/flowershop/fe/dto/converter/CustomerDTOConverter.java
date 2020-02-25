package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.AbstractDTO;
import com.accenture.flowershop.fe.dto.entity.AddressDTO;
import com.accenture.flowershop.fe.dto.entity.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public class CustomerDTOConverter extends AbstractDTOConverter<CustomerDTO, Customer> {

    @Autowired
    AddressDTOConverter addressDTOConverter;

    @Override
    public CustomerDTO convert(Customer entity) {
        CommonUtils.assertNull(entity, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_CUSTOMER));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAddress(addressDTOConverter.convert(entity.getAddress()));
        customerDTO.setBalance(entity.getBalance());
        customerDTO.setDiscount(entity.getDiscount());
        customerDTO.setEmail(entity.getEmail());
        customerDTO.setName(entity.getName());
        customerDTO.setSecondName(entity.getSecondName());
        customerDTO.setFatherName(entity.getFatherName());
        customerDTO.setPhone(entity.getPhone());
        entity.setId(entity.getId());
        return customerDTO;
    }
}
