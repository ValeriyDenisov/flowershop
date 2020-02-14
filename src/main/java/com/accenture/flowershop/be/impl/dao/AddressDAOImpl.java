package com.accenture.flowershop.be.impl.dao;

import com.accenture.flowershop.be.api.dao.AbstractDAO;
import com.accenture.flowershop.be.api.dao.AddressDAO;
import com.accenture.flowershop.be.entity.address.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl extends AbstractDAOImpl<Address> implements AddressDAO {

    @Override
    protected Class<Address> getType() {
        return Address.class;
    }
}
