package com.accenture.flowershop.test.integration.soap;

import com.accenture.flowershop.be.entity.customer.Customer;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public class CustomerSoapIntegrationTest extends AbstractSoapIntegrationTest {

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql"})
    public void createCustomerTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();

        xmlRequest
                .append("<createCustomerRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<name>name</name>")
                .append("\n\t<secondName>secondName</secondName>")
                .append("\n\t<addressId>1</addressId>")
                .append("\n\t<phone>1112223344</phone>")
                .append("\n\t<balance>111.11</balance>")
                .append("\n\t<discount>10</discount>")
                .append("\n\t<email>email@1</email>")
                .append("\n</createCustomerRequest>");
        xmlResponse
                .append("<createCustomerResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</createCustomerResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void findCustomerByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findCustomerByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</findCustomerByIdRequest>");
        xmlResponse
                .append("\n<findCustomerByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<customer>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<name>name_1</name>")
                .append("\n\t\t<secondName>secondName_1</secondName>")
                .append("\n\t\t<fatherName>fatherName_1</fatherName>")
                .append("\n\t\t<address>")
                .append("\n\t\t\t<id>1</id>")
                .append("\n\t\t\t<street>street_1</street>")
                .append("\n\t\t\t<city>city_1</city>")
                .append("\n\t\t\t<code>111</code>")
                .append("\n\t\t\t<building>1</building>")
                .append("\n\t\t</address>")
                .append("\n\t\t<phone>1113334455</phone>")
                .append("\n\t\t<balance>111.11</balance>")
                .append("\n\t\t<discount>1</discount>")
                .append("\n\t\t<email>email_1@1</email>")
                .append("\n\t</customer>")
                .append("\n</findCustomerByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void deleteCustomerByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<deleteCustomerByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</deleteCustomerByIdRequest>");
        xmlResponse
                .append("<deleteCustomerByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</deleteCustomerByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Customer customer = findById(1, Customer.class);
        Assert.assertNull(customer);
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void updateCustomerByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<updateCustomerByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n\t<name>name_303</name>")
                .append("\n</updateCustomerByIdRequest>");
        xmlResponse
                .append("<updateCustomerByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</updateCustomerByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Customer customer = findById(1, Customer.class);
        Assert.assertNotNull(customer);
        Assert.assertEquals("name_303", customer.getName());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/customer/create_customer_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void findAllCustomersTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findAllCustomersRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'/>");
        xmlResponse
                .append("<findAllCustomersResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<customers>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<name>name_1</name>")
                .append("\n\t\t<secondName>secondName_1</secondName>")
                .append("\n\t\t<fatherName>fatherName_1</fatherName>")
                .append("\n\t\t<address>")
                .append("\n\t\t\t<id>1</id>")
                .append("\n\t\t\t<street>street_1</street>")
                .append("\n\t\t\t<city>city_1</city>")
                .append("\n\t\t\t<code>111</code>")
                .append("\n\t\t\t<building>1</building>")
                .append("\n\t\t</address>")
                .append("\n\t\t<phone>1113334455</phone>")
                .append("\n\t\t<balance>111.11</balance>")
                .append("\n\t\t<discount>1</discount>")
                .append("\n\t\t<email>email_1@1</email>")
                .append("\n\t</customers>")
                .append("\n\t<customers>")
                .append("\n\t\t<id>2</id>")
                .append("\n\t\t<name>name_2</name>")
                .append("\n\t\t<secondName>secondName_2</secondName>")
                .append("\n\t\t<fatherName>fatherName_2</fatherName>")
                .append("\n\t\t<address>")
                .append("\n\t\t\t<id>2</id>")
                .append("\n\t\t\t<street>street_2</street>")
                .append("\n\t\t\t<city>city_2</city>")
                .append("\n\t\t\t<code>222</code>")
                .append("\n\t\t\t<building>2</building>")
                .append("\n\t\t</address>")
                .append("\n\t\t<phone>1112223344</phone>")
                .append("\n\t\t<balance>111.11</balance>")
                .append("\n\t\t<discount>1</discount>")
                .append("\n\t\t<email>email_2@1</email>")
                .append("\n\t</customers>")
                .append("\n</findAllCustomersResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        List<Customer> customers = findAll(Customer.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(customers));
        Assert.assertEquals(2, customers.size());
    }
}
