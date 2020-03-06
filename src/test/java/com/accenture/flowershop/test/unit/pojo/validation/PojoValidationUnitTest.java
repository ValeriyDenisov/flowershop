package com.accenture.flowershop.test.unit.pojo.validation;

import com.accenture.flowershop.be.entity.address.Address;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.flower.Flower;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.Set;

public class PojoValidationUnitTest {
    private static Validator validator;

    @BeforeClass
    public static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void FlowerValidationTest() {
        Flower flower;
        Set<ConstraintViolation<Flower>> violations;

        flower = new Flower.Builder(null, null, null).build();
        violations = validator.validate(flower);
        Assert.assertEquals(3, violations.size());

        flower = new Flower.Builder("name", -100.00, 5).build();
        violations = validator.validate(flower);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Flower price is negative!", violations.iterator().next().getMessage());

        flower = new Flower.Builder("nameewiofhewgwegihweoighwoeihgowiehgoiwehgoweihgoweihgoweihgoiwehgggggggggggweiohgiwehgoihweogihweioghewhigewg",
                100.00, 5).build();
        violations = validator.validate(flower);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Flower name size is incorrect!", violations.iterator().next().getMessage());
    }

    @Test
    public void CustomerValidateTest() {
        Address address;
        Customer customer;
        Set<ConstraintViolation<Customer>> violations;

        address = new Address.Builder(null, null, null, 34).build();
        customer = new Customer.Builder("name", "second_name", address, "1110002233",
                10.00, (short) 20, "email@email").build();
        violations = validator.validate(customer);
        Assert.assertEquals(3, violations.size());

        address = new Address.Builder("street", "city", 123, 34).build();
        customer = new Customer.Builder("name", "second_name", address, "441110002233",
                10.00, (short) 20, "email@email").build();
        violations = validator.validate(customer);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Customer phone number is incorrect!", violations.iterator().next().getMessage());


        customer = new Customer.Builder("name", "second_name", address, "1110002233",
                10.00, (short) 20, "email").build();
        violations = validator.validate(customer);
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("Customer email is incorrect!", violations.iterator().next().getMessage());
    }
}
