package com.accenture.flowershop.fe.endpoints;

import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.fe.endpoints.xjc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Calendar;

@Endpoint
public class OrderEndpoint extends AbstractEndpoint<XmlOrder, Order> {
    @Autowired
    OrderService orderService;

    @Override
    protected Class<XmlOrder> getXmlEntityType() {
        return XmlOrder.class;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "createOrderRequest")
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) {
        CreateOrderResponse createOrderResponse = new CreateOrderResponse();
        createOrderResponse.setResponse(getResponseFromEntityService(request, () -> {
            Calendar closeDate = null;
            if (request.getCloseDate() != null) {
                closeDate = request.getCloseDate().toGregorianCalendar();
            }
            orderService.insertOrder(request.getCustomerId(), request.getPrice(), request.isActive(),
                    request.getOpenDate().toGregorianCalendar(), closeDate);
        }));

        return createOrderResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findOrderByIdRequest")
    @ResponsePayload
    public FindOrderByIdResponse findOrderById(@RequestPayload FindOrderByIdRequest request) {
        FindOrderByIdResponse findOrderByIdResponse = new FindOrderByIdResponse();
        EndpointInformation<XmlOrder> endpointInformation = getResponseFromFindingOneEntity(request, () -> orderService.findOrderById(request.getId()));
        findOrderByIdResponse.setResponse(endpointInformation.getResponse());
        findOrderByIdResponse.setOrder(endpointInformation.getXmlEntity());
        return findOrderByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "updateOrderByIdRequest")
    @ResponsePayload
    public UpdateOrderByIdResponse updateOrder(@RequestPayload UpdateOrderByIdRequest request) {
        UpdateOrderByIdResponse updateOrderByIdResponse = new UpdateOrderByIdResponse();
        updateOrderByIdResponse.setResponse(getResponseFromEntityService(request, () -> {
            Calendar openDate = null;
            Calendar closeDate = null;
            if (request.getOpenDate() != null) {
                openDate = request.getOpenDate().toGregorianCalendar();
            }
            if (request.getCloseDate() != null) {
                closeDate = request.getCloseDate().toGregorianCalendar();
            }
            orderService.updateOrder(
                    request.getId(), request.getCustomerId(), request.getPrice(), request.isActive(),
                    openDate, closeDate);
        }));
        return updateOrderByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "deleteOrderByIdRequest")
    @ResponsePayload
    public DeleteOrderByIdResponse deleteOrder(@RequestPayload DeleteOrderByIdRequest request) {
        DeleteOrderByIdResponse deleteOrderByIdResponse = new DeleteOrderByIdResponse();
        deleteOrderByIdResponse.setResponse(getResponseFromEntityService(request, () -> orderService.deleteOrder(request.getId())));
        return deleteOrderByIdResponse;
    }

    @PayloadRoot(namespace = SCHEMA, localPart = "findAllOrdersRequest")
    @ResponsePayload
    public FindAllOrdersResponse findAllOrders(@RequestPayload FindAllOrdersRequest request) {
        FindAllOrdersResponse findAllOrdersResponse = new FindAllOrdersResponse();
        EndpointInformation<XmlOrder> endpointInformation = getResponseFromFindingAllEntities(request, findAllOrdersResponse.getOrders(), () -> orderService.findAllOrders());
        findAllOrdersResponse.setResponse(endpointInformation.getResponse());
        return findAllOrdersResponse;
    }
}
