package com.accenture.flowershop.fe.endpoints;

import com.accenture.flowershop.be.api.service.UserService;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.fe.endpoints.xjc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint extends AbstractEndpoint<XmlUser, User> {
    @Autowired
    UserService UserService;

    @Override
    protected Class<XmlUser> getXmlEntityType() {
        return XmlUser.class;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setResponse(getResponseFromEntityService(request, () -> UserService.insertUser(
                request.getLogin(), request.getPassword()
        )));
        return createUserResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findUserByIdRequest")
    @ResponsePayload
    public FindUserByIdResponse findUserById(@RequestPayload FindUserByIdRequest request) {
        FindUserByIdResponse findUserByIdResponse = new FindUserByIdResponse();
        EndpointInformation<XmlUser> endpointInformation = getResponseFromFindingOneEntity(request, () -> UserService.findUserById(request.getId()));
        findUserByIdResponse.setResponse(endpointInformation.getResponse());
        findUserByIdResponse.setUser(endpointInformation.getXmlEntity());
        return findUserByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "updateUserByIdRequest")
    @ResponsePayload
    public UpdateUserByIdResponse updateUser(@RequestPayload UpdateUserByIdRequest request) {
        UpdateUserByIdResponse updateUserByIdResponse = new UpdateUserByIdResponse();
        updateUserByIdResponse.setResponse(getResponseFromEntityService(request, () -> UserService.updateUser(
                request.getId(), request.getLogin(), request.getPassword()
        )));
        return updateUserByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "deleteUserByIdRequest")
    @ResponsePayload
    public DeleteUserByIdResponse deleteUser(@RequestPayload DeleteUserByIdRequest request) {
        DeleteUserByIdResponse deleteUserByIdResponse = new DeleteUserByIdResponse();
        deleteUserByIdResponse.setResponse(getResponseFromEntityService(request, () -> UserService.deleteUser(request.getId())));
        return deleteUserByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findAllUsersRequest")
    @ResponsePayload
    public FindAllUsersResponse findAllUsers(@RequestPayload FindAllUsersRequest request) {
        FindAllUsersResponse findAllUsersResponse = new FindAllUsersResponse();
        EndpointInformation<XmlUser> endpointInformation = getResponseFromFindingAllEntities(request, findAllUsersResponse.getUsers(), () -> UserService.findAllUsers());
        findAllUsersResponse.setResponse(endpointInformation.getResponse());
        return findAllUsersResponse;
    }
}
