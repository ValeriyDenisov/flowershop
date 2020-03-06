package com.accenture.flowershop.test.integration.soap;

import com.accenture.flowershop.fe.endpoints.AbstractEndpoint;
import com.accenture.flowershop.test.integration.AbstractIntegrationTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

@ContextConfiguration(locations = "classpath*:config/test-spring-ws-servlet.xml")
public abstract class AbstractSoapIntegrationTest extends AbstractIntegrationTest {
    public static final String SCHEMA = AbstractEndpoint.SCHEMA;

    @Autowired
    ApplicationContext context;

    protected MockWebServiceClient mockClient;

    @Before
    public void init() {
        if (mockClient == null) {
            mockClient = MockWebServiceClient.createClient(context);
        }
    }

    protected void sendRequestAndGetResponse(String xmlRequestAsString, String xmlResponseAsString) {
        Source request = new StringSource(xmlRequestAsString);
        Source response = new StringSource(xmlResponseAsString);

        mockClient
                .sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(payload(response));
    }

}
