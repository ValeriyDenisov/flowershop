package com.accenture.flowershop.test.integration.soap;


import com.accenture.flowershop.be.entity.order.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public class OrderSoapIntegrationTest extends AbstractSoapIntegrationTest {
    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql"})
    public void createOrderTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();

        xmlRequest
                .append("<createOrderRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<price>111.11</price>")
                .append("\n\t<customerId>1</customerId>")
                .append("\n\t<active>true</active>")
                .append("\n\t<openDate>2020-01-20</openDate>")
                .append("\n</createOrderRequest>");
        xmlResponse
                .append("<createOrderResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</createOrderResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql",
            "/sql/order/insert_order.sql"})
    public void findOrderByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findOrderByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</findOrderByIdRequest>");
        xmlResponse
                .append("<findOrderByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<order>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<price>111.11</price>")
                .append("\n\t\t<customer>")
                .append("\n\t\t\t<id>1</id>")
                .append("\n\t\t\t<name>name_1</name>")
                .append("\n\t\t\t<secondName>secondName_1</secondName>")
                .append("\n\t\t\t<fatherName>fatherName_1</fatherName>")
                .append("\n\t\t\t<address>")
                .append("\n\t\t\t\t<id>1</id>")
                .append("\n\t\t\t\t<street>street_1</street>")
                .append("\n\t\t\t\t<city>city_1</city>")
                .append("\n\t\t\t\t<code>111</code>")
                .append("\n\t\t\t\t<building>1</building>")
                .append("\n\t\t\t</address>")
                .append("\n\t\t\t<phone>1113334455</phone>")
                .append("\n\t\t\t<balance>111.11</balance>")
                .append("\n\t\t\t<discount>1</discount>")
                .append("\n\t\t\t<email>email_1@1</email>")
                .append("\n\t\t</customer>")
                .append("\n\t\t<active>true</active>")
                .append("\n\t\t<openDate>2020-01-21+03:00</openDate>")
                .append("\n\t</order>")
                .append("\n</findOrderByIdResponse>");

        Order order =findById(1, Order.class);
        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql",
            "/sql/order/insert_order.sql"})
    public void deleteOrderByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<deleteOrderByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</deleteOrderByIdRequest>");
        xmlResponse
                .append("<deleteOrderByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</deleteOrderByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Order order = findById(1, Order.class);
        Assert.assertNull(order);
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql",
            "/sql/order/insert_order.sql"})
    public void updateOrderByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<updateOrderByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n\t<price>777.77</price>")
                .append("\n</updateOrderByIdRequest>");
        xmlResponse
                .append("<updateOrderByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</updateOrderByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Order order = findById(1, Order.class);
        Assert.assertNotNull(order);
        Assert.assertEquals((Double) 777.77, order.getPrice());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/order/create_order_table.sql",
            "/sql/address/insert_address.sql",
            "/sql/customer/insert_customer.sql",
            "/sql/order/insert_order.sql"})
    public void findAllOrdersTest() {
        StringBuilder xmlRequest = new StringBuilder();
        xmlRequest
                .append("<findAllOrdersRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'/>");
        String xmlResponse = "<ns2:findAllOrdersResponse xmlns:ns2=\"http://accenture.com/flowershop/schemas\"><ns2:response><ns2:status><ns2:status>SUCCESS</ns2:status></ns2:status></ns2:response><ns2:orders><ns2:id>1</ns2:id><ns2:price>111.11</ns2:price><ns2:customer><ns2:id>1</ns2:id><ns2:name>name_1</ns2:name><ns2:secondName>secondName_1</ns2:secondName><ns2:fatherName>fatherName_1</ns2:fatherName><ns2:address><ns2:id>1</ns2:id><ns2:street>street_1</ns2:street><ns2:city>city_1</ns2:city><ns2:code>111</ns2:code><ns2:building>1</ns2:building></ns2:address><ns2:phone>1113334455</ns2:phone><ns2:balance>111.11</ns2:balance><ns2:discount>1</ns2:discount><ns2:email>email_1@1</ns2:email></ns2:customer><ns2:active>true</ns2:active><ns2:openDate>2020-01-21+03:00</ns2:openDate></ns2:orders><ns2:orders><ns2:id>2</ns2:id><ns2:price>222.22</ns2:price><ns2:customer><ns2:id>2</ns2:id><ns2:name>name_2</ns2:name><ns2:secondName>secondName_2</ns2:secondName><ns2:fatherName>fatherName_2</ns2:fatherName><ns2:address><ns2:id>2</ns2:id><ns2:street>street_2</ns2:street><ns2:city>city_2</ns2:city><ns2:code>222</ns2:code><ns2:building>2</ns2:building></ns2:address><ns2:phone>1112223344</ns2:phone><ns2:balance>111.11</ns2:balance><ns2:discount>1</ns2:discount><ns2:email>email_2@1</ns2:email></ns2:customer><ns2:active>false</ns2:active><ns2:openDate>2020-01-22+03:00</ns2:openDate><ns2:closeDate>2020-01-02+03:00</ns2:closeDate></ns2:orders></ns2:findAllOrdersResponse>";
        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse);

        List<Order> orders = findAll(Order.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(orders));
        Assert.assertEquals(2, orders.size());
    }

}
