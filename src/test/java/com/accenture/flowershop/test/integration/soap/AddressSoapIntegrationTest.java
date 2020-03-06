package com.accenture.flowershop.test.integration.soap;

import com.accenture.flowershop.be.entity.address.Address;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

public class AddressSoapIntegrationTest extends AbstractSoapIntegrationTest {

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql"})
    public void createAddressTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<createAddressRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<city>city</city>")
                .append("\n\t<street>street</street>")
                .append("\n\t<code>100</code>")
                .append("\n\t<building>23</building>")
                .append("\n</createAddressRequest>");
        xmlResponse
                .append("<createAddressResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</createAddressResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void updateAddressTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<updateAddressByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n\t<building>15</building>")
                .append("\n</updateAddressByIdRequest>");
        xmlResponse
                .append("<updateAddressByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</updateAddressByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Address address = findById(1, Address.class);
        Assert.assertNotNull(address);
        Assert.assertEquals(15, address.getBuilding());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void deleteAddressTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<deleteAddressByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n</deleteAddressByIdRequest>");
        xmlResponse
                .append("<deleteAddressByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</deleteAddressByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Address address = findById(1, Address.class);
        Assert.assertNull(address);
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void findAllAddressesTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findAllAddressesRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n</findAllAddressesRequest>");
        xmlResponse
                .append("<findAllAddressesResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<addresses>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<city>city_1</city>")
                .append("\n\t\t<street>street_1</street>")
                .append("\n\t\t<code>111</code>")
                .append("\n\t\t<building>1</building>")
                .append("\n\t</addresses>")
                .append("\n\t<addresses>")
                .append("\n\t\t<id>2</id>")
                .append("\n\t\t<city>city_2</city>")
                .append("\n\t\t<street>street_2</street>")
                .append("\n\t\t<code>222</code>")
                .append("\n\t\t<building>2</building>")
                .append("\n\t</addresses>")
                .append("\n</findAllAddressesResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/address/create_address_table.sql",
            "/sql/address/insert_address.sql"})
    public void findAddressByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findAddressByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</findAddressByIdRequest>");
        xmlResponse
                .append("<findAddressByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<address>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<city>city_1</city>")
                .append("\n\t\t<street>street_1</street>")
                .append("\n\t\t<code>111</code>")
                .append("\n\t\t<building>1</building>")
                .append("\n\t</address>")
                .append("\n</findAddressByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }
}
