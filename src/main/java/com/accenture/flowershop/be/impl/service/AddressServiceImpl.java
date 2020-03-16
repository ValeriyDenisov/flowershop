package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.AddressDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.address.Address_;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AddressServiceImpl extends AbstractServiceImpl<Address, AddressDAO> implements AddressService {
    public static final String ADDRESS_CLASS_NAME = Address.class.getSimpleName();

    private final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    AddressDAO addressDAO;

    @Override
    public String getEntityName() {
        return ADDRESS_CLASS_NAME;
    }

    @Override
    public AddressDAO getDAO() {
        return addressDAO;
    }

    @Override
    public Integer insertAddress(String street, String city, Integer code, Integer building) throws EntityCreatingException {
        return insertEntity(getAddressMandatoryFieldsValues(street, city, code, building), () -> createAddress(street, city, code, building));
    }

    @Override
    public void deleteAddress(Integer id) throws EntityDeletingException {
        deleteEntityByUniqueField(Address_.ID, id);
    }

    @Override
    public void updateAddress(Integer id, String street, String city, Integer code, Integer building) throws EntityUpdatingException {
        updateEntityByUniqueField(Address_.ID, id, (address) -> updateAddressFields(address, street, city, code, building));
    }

    @Override
    public Address findAddressById(Integer id) throws EntityFindingException {
        return findEntityByUniqueField(Address_.ID, id);
    }

    @Override
    public List<Address> findAllAddresses() {
        return findAllEntities();
    }

    protected Address createAddress(String street, String city, Integer code, Integer building) {
        return new Address.Builder(street, city, code, building).build();
    }

    private Map<String, Object> getAddressMandatoryFieldsValues(String street,
                                                                String city, Integer code, Integer building) {
        Map<String, Object> fieldsValues = new HashMap<>();

        fieldsValues.put(Address_.STREET, street);
        fieldsValues.put(Address_.CITY, city);
        fieldsValues.put(Address_.CODE, code);
        fieldsValues.put(Address_.BUILDING, building);

        return fieldsValues;
    }

    private Map<String, Object> getAddressMandatoryFieldsValues(Integer id, String street, String city, Integer code, Integer building) {
        Map<String, Object> fieldValues = getAddressMandatoryFieldsValues(street, city, code, building);
        fieldValues.put(Address_.ID, id);
        return fieldValues;
    }

    private void updateAddressFields(Address address, String street, String city, Integer code, Integer building) {
        if (StringUtils.isNotEmpty(street)) {
            address.setStreet(street);
        }
        if (StringUtils.isNotEmpty(city)) {
            address.setCity(city);
        }
        if (code != null && code != 0) {
            address.setCode(code);
        }
        if (building != null && building != 0) {
            address.setBuilding(building);
        }
    }
}
