package com.accenture.flowershop.test.unit.soap.client;

import com.accenture.flowershop.fe.endpoints.xjc.CreateAddressRequest;
import com.accenture.flowershop.fe.endpoints.xjc.CreateAddressResponse;
import com.accenture.flowershop.fe.endpoints.xjc.FindAddressByIdRequest;
import com.accenture.flowershop.fe.endpoints.xjc.FindAddressByIdResponse;
import com.accenture.flowershop.test.unit.soap.AbstractSoapUnitTest;

public class AddressClient extends AbstractClient {
    public CreateAddressResponse createAddress(String city, String street, int code, int building) {
        CreateAddressRequest request = new CreateAddressRequest();
        request.setBuilding(building);
        request.setCity(city);
        request.setCode(code);
        request.setStreet(street);

        return (CreateAddressResponse) getResponse(request);
    }

    public FindAddressByIdResponse findAddressById(int id) {
        FindAddressByIdRequest request = new FindAddressByIdRequest();
        request.setId(id);

        return (FindAddressByIdResponse) getResponse(request);
    }
}
