package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.AbstractDTO;
import com.accenture.flowershop.fe.dto.entity.AddressDTO;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;

@Component
public class AddressDTOConverter extends AbstractDTOConverter<AddressDTO, Address> {

    @Override
    public AddressDTO convert(Address entity) {
        CommonUtils.assertNull(entity, MessageFormat.format(ERROR_ENTITY_NULL, Constants.ENTITY_ADDRESS));

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(entity.getId());
        addressDTO.setBuilding(entity.getBuilding());
        addressDTO.setCity(entity.getCity());
        addressDTO.setStreet(entity.getStreet());
        addressDTO.setCode(entity.getCode());
        return addressDTO;
    }
}
