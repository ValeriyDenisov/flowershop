package com.accenture.flowershop.test.unit.soap;

import com.accenture.flowershop.fe.endpoints.xjc.CreateAddressResponse;
import com.accenture.flowershop.test.unit.soap.client.AddressClient;
import org.junit.Test;

public class AddressSoapUnitTest extends AbstractSoapUnitTest<AddressClient> {
    AddressClient client;

    @Override
    public AddressClient getClient() {
        if (client == null) {
            client = new AddressClient();
        }
        return client;
    }
}
