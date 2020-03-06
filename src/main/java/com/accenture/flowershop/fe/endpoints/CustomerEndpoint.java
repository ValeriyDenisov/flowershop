package com.accenture.flowershop.fe.endpoints;

import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.fe.endpoints.xjc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CustomerEndpoint extends AbstractEndpoint<XmlCustomer, Customer> {
    @Autowired
    CustomerService customerService;

    @Override
    protected Class<XmlCustomer> getXmlEntityType() {
        return XmlCustomer.class;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "createCustomerRequest")
    @ResponsePayload
    public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest request) {
        CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
        createCustomerResponse.setResponse(getResponseFromEntityService(request, () -> customerService.insertCustomer(
                request.getName(), request.getSecondName(), request.getFatherName(), request.getAddressId(), request.getPhone(),
                request.getBalance(), request.getDiscount(), request.getEmail()
        )));
        return createCustomerResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findCustomerByIdRequest")
    @ResponsePayload
    public FindCustomerByIdResponse findCustomerById(@RequestPayload FindCustomerByIdRequest request) {
        FindCustomerByIdResponse findCustomerByIdResponse = new FindCustomerByIdResponse();
        EndpointInformation<XmlCustomer> endpointInformation = getResponseFromFindingOneEntity(request, () -> customerService.findCustomerById(request.getId()));
        findCustomerByIdResponse.setResponse(endpointInformation.getResponse());
        findCustomerByIdResponse.setCustomer(endpointInformation.getXmlEntity());
        return findCustomerByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "updateCustomerByIdRequest")
    @ResponsePayload
    public UpdateCustomerByIdResponse updateCustomer(@RequestPayload UpdateCustomerByIdRequest request) {
        UpdateCustomerByIdResponse updateCustomerByIdResponse = new UpdateCustomerByIdResponse();
        updateCustomerByIdResponse.setResponse(getResponseFromEntityService(request, () -> customerService.updateCustomer(
                request.getId(), request.getName(), request.getSecondName(), request.getFatherName(), request.getAddressId(),
                request.getPhone(), request.getBalance(), request.getDiscount(), request.getEmail()
        )));
        return updateCustomerByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "deleteCustomerByIdRequest")
    @ResponsePayload
    public DeleteCustomerByIdResponse deleteCustomer(@RequestPayload DeleteCustomerByIdRequest request) {
        DeleteCustomerByIdResponse deleteCustomerByIdResponse = new DeleteCustomerByIdResponse();
        deleteCustomerByIdResponse.setResponse(getResponseFromEntityService(request, () -> customerService.deleteCustomer(request.getId())));
        return deleteCustomerByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findAllCustomersRequest")
    @ResponsePayload
    public FindAllCustomersResponse findAllCustomers(@RequestPayload FindAllCustomersRequest request) {
        FindAllCustomersResponse findAllCustomersResponse = new FindAllCustomersResponse();
        EndpointInformation<XmlCustomer> endpointInformation = getResponseFromFindingAllEntities(request, findAllCustomersResponse.getCustomers(), () -> customerService.findAllCustomers());
        findAllCustomersResponse.setResponse(endpointInformation.getResponse());
        return findAllCustomersResponse;
    }
}
