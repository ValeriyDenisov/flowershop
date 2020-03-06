package com.accenture.flowershop.fe.endpoints;

import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.exceptions.EntityUpdatingException;
import com.accenture.flowershop.be.api.service.AddressService;
import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.impl.utils.Constants;
import com.accenture.flowershop.fe.endpoints.xjc.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.MessageFormat;
import java.util.List;

@Endpoint
public class AddressEndpoint extends AbstractEndpoint<XmlAddress, Address> {
    @Autowired
    AddressService addressService;

    @Override
    protected Class<XmlAddress> getXmlEntityType() {
        return XmlAddress.class;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "createAddressRequest")
    @ResponsePayload
    public CreateAddressResponse createAddress(@RequestPayload CreateAddressRequest request) {
        CreateAddressResponse createAddressResponse = new CreateAddressResponse();
        createAddressResponse.setResponse(getResponseFromEntityService(request, () -> addressService.insertAddress(request.getStreet(), request.getCity(), request.getCode(),
                request.getBuilding())));
        return createAddressResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findAddressByIdRequest")
    @ResponsePayload
    public FindAddressByIdResponse findAddressById(@RequestPayload FindAddressByIdRequest request) {
        FindAddressByIdResponse findAddressByIdResponse = new FindAddressByIdResponse();
        EndpointInformation<XmlAddress> endpointInformation = getResponseFromFindingOneEntity(request, () -> addressService.findAddressById(request.getId()));
        findAddressByIdResponse.setResponse(endpointInformation.getResponse());
        findAddressByIdResponse.setAddress(endpointInformation.getXmlEntity());
        return findAddressByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "updateAddressByIdRequest")
    @ResponsePayload
    public UpdateAddressByIdResponse updateAddress(@RequestPayload UpdateAddressByIdRequest request) {
        UpdateAddressByIdResponse updateAddressByIdResponse = new UpdateAddressByIdResponse();
        updateAddressByIdResponse.setResponse(getResponseFromEntityService(request, () -> addressService.updateAddress(request.getId(), request.getStreet(), request.getCity(), request.getCode(),
                request.getBuilding())));
        return updateAddressByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "deleteAddressByIdRequest")
    @ResponsePayload
    public DeleteAddressByIdResponse deleteAddress(@RequestPayload DeleteAddressByIdRequest request) {
        DeleteAddressByIdResponse deleteAddressByIdResponse = new DeleteAddressByIdResponse();
        deleteAddressByIdResponse.setResponse(getResponseFromEntityService(request, () -> addressService.deleteAddress(request.getId())));
        return deleteAddressByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findAllAddressesRequest")
    @ResponsePayload
    public FindAllAddressesResponse findAllAddresses(@RequestPayload FindAllAddressesRequest request) {
        FindAllAddressesResponse findAllAddressesResponse = new FindAllAddressesResponse();
        EndpointInformation<XmlAddress> endpointInformation = getResponseFromFindingAllEntities(request, findAllAddressesResponse.getAddresses() ,() -> addressService.findAllAddresses());
        findAllAddressesResponse.setResponse(endpointInformation.getResponse());
        return findAllAddressesResponse;
    }
}