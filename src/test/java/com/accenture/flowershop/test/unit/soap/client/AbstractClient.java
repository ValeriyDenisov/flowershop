package com.accenture.flowershop.test.unit.soap.client;

import com.accenture.flowershop.fe.endpoints.xjc.OperationStatus;
import com.accenture.flowershop.fe.endpoints.xjc.Response;
import com.accenture.flowershop.fe.endpoints.xjc.Status;
import com.accenture.flowershop.test.unit.soap.AbstractSoapUnitTest;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class AbstractClient extends WebServiceGatewaySupport {
    protected Object getResponse(Object request) {
        WebServiceTemplate template = getWebServiceTemplate();
        template.setCheckConnectionForFault(false);
        return template.marshalSendAndReceive("http://localhost:8081/soap/flowershop.wsdl", request);
    }
}
