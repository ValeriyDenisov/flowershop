package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.AbstractDTO;
import com.accenture.flowershop.fe.dto.entity.AddressDTO;

import java.text.MessageFormat;
import java.util.Map;

public class AddressDTOConverter extends AbstractDTOConverter<Address> {

    public static AddressDTO convert(Address address) {
        CommonUtils.assertNull(address, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_ADDRESS));

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setBuilding(address.getBuilding());
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCode(address.getCode());

        return addressDTO;
    }

    private AddressDTOConverter() {}
}
