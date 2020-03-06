package com.accenture.flowershop.test.unit.soap;

import com.accenture.flowershop.fe.endpoints.AbstractEndpoint;
import org.junit.Before;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public abstract class AbstractSoapUnitTest<T extends WebServiceGatewaySupport> {
    public static final String CONTEXT_PATH = "com.accenture.flowershop.fe.endpoints.xjc";
    public static final String CONTEXT_URI = AbstractEndpoint.SCHEMA;

    public abstract T getClient();

    protected Jaxb2Marshaller marshaller;

    @Before
    public void init() {
        if (marshaller == null) {
            marshaller = new Jaxb2Marshaller();
            marshaller.setContextPath(CONTEXT_PATH);
        }
        getClient().setMarshaller(marshaller);
        getClient().setUnmarshaller(marshaller);
    }
}
