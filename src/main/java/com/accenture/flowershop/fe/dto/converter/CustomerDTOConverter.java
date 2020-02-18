package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.AbstractDTO;
import com.accenture.flowershop.fe.dto.entity.AddressDTO;
import com.accenture.flowershop.fe.dto.entity.CustomerDTO;

import java.text.MessageFormat;

public class CustomerDTOConverter extends AbstractDTOConverter<Customer> {

    public static CustomerDTO convert(Customer customer) {
        CommonUtils.assertNull(customer, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_CUSTOMER));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setAddress(AddressDTOConverter.convert(customer.getAddress()));
        customerDTO.setBalance(customer.getBalance());
        customerDTO.setDiscount(customer.getDiscount());
        customerDTO.setFatherName(customer.getFatherName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setSecondName(customer.getSecondName());
        return customerDTO;
    }

    private CustomerDTOConverter() {
    }
}
