package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.dao.AddressDAO;
import com.accenture.flowershop.be.api.exceptions.EntityCreatingException;
import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityFindingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import com.accenture.flowershop.be.impl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl extends AbstractServiceImpl implements AddressService {

    @Autowired
    AddressDAO addressDAO;

    public Integer insertAddress(String street, String city, Integer code, Integer building) throws EntityCreatingException {
        CommonUtils.assertValues(getAddressFieldsValues(null, street, city, code, building));

        Address address = createAddress(street, city, code, building);
        validateEntity(address, (ex) -> {
            throw new EntityCreatingException(ex);
        });

        addressDAO.insert(address);
        return address.getId();
    }

    public void deleteAddress(Integer id) throws EntityDeletingException {
        CommonUtils.assertNull(id, ERROR_ENTITY_ID_NULL);

        Address address = findAddressById(id);
        if (address == null) {
            EntityFindingException e = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, id));
            throw new EntityDeletingException(e);
        }

        addressDAO.delete(address);
    }

    public void updateAddress(Integer id, String street, String city, Integer code, Integer building) throws EntityUpdatingException {
        CommonUtils.assertValues(getAddressFieldsValues(id, street, city, code, building));

        Address address = findAddressById(id);
        if (address == null) {
            EntityFindingException e = new EntityFindingException(MessageFormat.format(ERROR_ENTITY_BY_ID_NOT_FOUND, Customer.class, id));
            throw new EntityUpdatingException(e);
        }

        address.setStreet(street);
        address.setCode(code);
        address.setCity(city);
        address.setBuilding(building);
        validateEntity(address, (ex) -> {
            throw new EntityUpdatingException(ex);
        });

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

    private Map<String, Object> getAddressFieldsValues(Integer id, String street,
                                                       String city, Integer code, Integer building) {
        Map<String, Object> fieldsValues = new HashMap<String, Object>();

        if (id != null) {
            fieldsValues.put(Constants.ENTITY_ID, id);
        }
        fieldsValues.put(Constants.ADDRESS_STREET, street);
        fieldsValues.put(Constants.ADDRESS_CITY, city);
        fieldsValues.put(Constants.ADDRESS_CODE, code);
        fieldsValues.put(Constants.ADDRESS_BUILDING, building);

        return fieldsValues;
    }
}
