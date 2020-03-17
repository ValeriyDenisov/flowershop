package com.accenture.flowershop.fe.rest;

import com.accenture.flowershop.be.api.exceptions.EntityDeletingException;
import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.fe.dto.converter.CustomerDTOConverter;
import com.accenture.flowershop.fe.dto.entity.CustomerDTO;
import com.accenture.flowershop.fe.endpoints.xjc.OperationStatus;
import com.accenture.flowershop.fe.endpoints.xjc.Response;
import com.accenture.flowershop.fe.endpoints.xjc.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/customer")
public class CustomerRestController extends AbstractRestController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerDTOConverter customerDTOConverter;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerDTOConverter.convertAll(customerService.findAllCustomers());
    }

    @DeleteMapping(value = "/{id}")
    public OperationStatus deleteCustomer(@PathVariable Integer id) {
        OperationStatus status;
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            status = createErrorStatus("Customer with id: " + id + " not exists");
        } else {
            try {
                customerService.deleteCustomer(id);
                status = createStatus(Status.SUCCESS);
            } catch (EntityDeletingException e) {
                status = createErrorStatus(e.getMessage());
            }
        }

        return status;
    }
}
