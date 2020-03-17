package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.dto.entity.AddressDTO;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class AddressDTOConverter extends AbstractDTOConverter<AddressDTO, Address> {

    @Override
    public Class<AddressDTO> getDTOType() {
        return AddressDTO.class;
    }
}
