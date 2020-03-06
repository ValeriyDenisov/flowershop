package com.accenture.flowershop.test.integration.soap;

import com.accenture.flowershop.be.entity.flower.Flower;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

public class FlowerSoapIntegrationTest extends AbstractSoapIntegrationTest {

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql"})
    public void createFlowerTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<createFlowerRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<name>name_1</name>")
                .append("\n\t<price>111.11</price>")
                .append("\n\t<quantityInStock>10</quantityInStock>")
                .append("\n</createFlowerRequest>");
        xmlResponse
                .append("<createFlowerResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</createFlowerResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void updateFlowerTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<updateFlowerByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n\t<quantityInStock>15</quantityInStock>")
                .append("\n</updateFlowerByIdRequest>");
        xmlResponse
                .append("<updateFlowerByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</updateFlowerByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Flower Flower = findById(1, Flower.class);
        Assert.assertNotNull(Flower);
        Assert.assertEquals((Integer) 15, Flower.getQuantityInStock());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void deleteFlowerTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<deleteFlowerByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<id>1</id>")
                .append("\n</deleteFlowerByIdRequest>");
        xmlResponse
                .append("<deleteFlowerByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n</deleteFlowerByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());

        Flower Flower = findById(1, Flower.class);
        Assert.assertNull(Flower);
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void findAllFlowersTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findAllFlowersRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n</findAllFlowersRequest>");
        xmlResponse
                .append("<findAllFlowersResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<flowers>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<name>name_1</name>")
                .append("\n\t\t<price>111.11</price>")
                .append("\n\t\t<quantityInStock>1</quantityInStock>")
                .append("\n\t</flowers>")
                .append("\n\t<flowers>")
                .append("\n\t\t<id>2</id>")
                .append("\n\t\t<name>name_2</name>")
                .append("\n\t\t<price>222.22</price>")
                .append("\n\t\t<quantityInStock>2</quantityInStock>")
                .append("\n\t</flowers>")
                .append("\n</findAllFlowersResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }

    @Test
    @Sql({"/sql/delete_data_tables.sql",
            "/sql/flower/create_flower_table.sql",
            "/sql/flower/insert_flower_service.sql"})
    public void findFlowerByIdTest() {
        StringBuilder xmlRequest = new StringBuilder();
        StringBuilder xmlResponse = new StringBuilder();
        xmlRequest
                .append("<findFlowerByIdRequest xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("<id>1</id>")
                .append("\n</findFlowerByIdRequest>");
        xmlResponse
                .append("<findFlowerByIdResponse xmlns=")
                .append("'")
                .append(SCHEMA)
                .append("'>")
                .append("\n\t<response>")
                .append("\n\t\t<status>")
                .append("\n\t\t\t<status>SUCCESS</status>")
                .append("\n\t\t</status>")
                .append("\n\t</response>")
                .append("\n\t<flower>")
                .append("\n\t\t<id>1</id>")
                .append("\n\t\t<name>name_1</name>")
                .append("\n\t\t<price>111.11</price>")
                .append("\n\t\t<quantityInStock>1</quantityInStock>")
                .append("\n\t</flower>")
                .append("\n</findFlowerByIdResponse>");

        sendRequestAndGetResponse(xmlRequest.toString(), xmlResponse.toString());
    }
}
