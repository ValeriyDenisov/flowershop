package com.accenture.flowershop.fe.endpoints;

import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.fe.endpoints.xjc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FlowerEndpoint extends AbstractEndpoint<XmlFlower, Flower> {
    @Autowired
    FlowerService flowerService;

    @Override
    protected Class<XmlFlower> getXmlEntityType() {
        return XmlFlower.class;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "createFlowerRequest")
    @ResponsePayload
    public CreateFlowerResponse createFlower(@RequestPayload CreateFlowerRequest request) {
        CreateFlowerResponse createFlowerResponse = new CreateFlowerResponse();
        createFlowerResponse.setResponse(getResponseFromEntityService(request, () -> flowerService.insertFlower(
                request.getName(), request.getPrice(), request.getQuantityInStock()
        )));
        return createFlowerResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findFlowerByIdRequest")
    @ResponsePayload
    public FindFlowerByIdResponse findFlowerById(@RequestPayload FindFlowerByIdRequest request) {
        FindFlowerByIdResponse findFlowerByIdResponse = new FindFlowerByIdResponse();
        EndpointInformation<XmlFlower> endpointInformation = getResponseFromFindingOneEntity(request, () -> flowerService.findFlowerById(request.getId()));
        findFlowerByIdResponse.setResponse(endpointInformation.getResponse());
        findFlowerByIdResponse.setFlower(endpointInformation.getXmlEntity());
        return findFlowerByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "updateFlowerByIdRequest")
    @ResponsePayload
    public UpdateFlowerByIdResponse updateFlower(@RequestPayload UpdateFlowerByIdRequest request) {
        UpdateFlowerByIdResponse updateFlowerByIdResponse = new UpdateFlowerByIdResponse();
        updateFlowerByIdResponse.setResponse(getResponseFromEntityService(request, () -> flowerService.updateFlower(
                request.getId(), request.getName(), request.getPrice(), request.getQuantityInStock()
        )));
        return updateFlowerByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "deleteFlowerByIdRequest")
    @ResponsePayload
    public DeleteFlowerByIdResponse deleteFlower(@RequestPayload DeleteFlowerByIdRequest request) {
        DeleteFlowerByIdResponse deleteFlowerByIdResponse = new DeleteFlowerByIdResponse();
        deleteFlowerByIdResponse.setResponse(getResponseFromEntityService(request, () -> flowerService.deleteFlower(request.getId())));
        return deleteFlowerByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findAllFlowersRequest")
    @ResponsePayload
    public FindAllFlowersResponse findAllFlowers(@RequestPayload FindAllFlowersRequest request) {
        FindAllFlowersResponse findAllFlowersResponse = new FindAllFlowersResponse();
        EndpointInformation<XmlFlower> endpointInformation = getResponseFromFindingAllEntities(request, findAllFlowersResponse.getFlowers(), () -> flowerService.findAllFlowers());
        findAllFlowersResponse.setResponse(endpointInformation.getResponse());
        return findAllFlowersResponse;
    }

}
