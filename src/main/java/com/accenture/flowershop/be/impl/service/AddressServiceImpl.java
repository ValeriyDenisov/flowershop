package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.AddressDAO;
import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class AddressServiceImpl extends AbstractServiceImpl<Address> implements AddressService {
    public static final String ERROR_ADDRESS_STREET_EMPTY = "Поле street адресса пусто!";
    public static final String ERROR_ADDRESS_CITY_EMPTY = "Поле city адресса пусто!";
    public static final String ERROR_ADDRESS_CODE_NULL = "Поле code адресса пусто!";
    public static final String ERROR_ADDRESS_BUILDING_NULL = "Поле building адресса пусто!";

    @Autowired
    AddressDAO addressDAO;

    public void insertAddress(String street, String city, Integer code, Integer building) {
        CommonUtils.assertEmpty(street, ERROR_ADDRESS_STREET_EMPTY);
        CommonUtils.assertEmpty(city, ERROR_ADDRESS_CITY_EMPTY);
        CommonUtils.assertPrimitiveIntNull(code, ERROR_ADDRESS_CODE_NULL);
        CommonUtils.assertPrimitiveIntNull(building, ERROR_ADDRESS_BUILDING_NULL);

        Address address = createAddress(street, city, code, building);
        addressDAO.insert(address);
    }

    public void deleteAddress(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        Address address = findAddressById(id);
        if (address == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, id));
        }

        addressDAO.delete(address);
    }

    public void updateAddress(Integer id, String street, String city, Integer code, Integer building) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);
        CommonUtils.assertEmpty(street, ERROR_ADDRESS_STREET_EMPTY);
        CommonUtils.assertEmpty(city, ERROR_ADDRESS_CITY_EMPTY);
        CommonUtils.assertPrimitiveIntNull(code, ERROR_ADDRESS_CODE_NULL);
        CommonUtils.assertPrimitiveIntNull(building, ERROR_ADDRESS_BUILDING_NULL);

        Address address = findAddressById(id);
        if (address == null) {
            throw new EntityException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, id));
        }

        address.setStreet(street);
        address.setCode(code);
        address.setCity(city);
        address.setBuilding(building);
        addressDAO.update(address);

    }

    public Address findAddressById(Integer id) {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        return addressDAO.findById(id);
    }

    public List<Address> findAllAddresses() {
        return addressDAO.findAll();
    }

    protected Address createAddress(String street, String city, Integer code, Integer building) {
        return new Address.Builder(street, city, code, building).build();
    }

    private boolean isAddressExist(Integer id) {
        Address address = addressDAO.findById(id);
        return address != null;
    }
}
