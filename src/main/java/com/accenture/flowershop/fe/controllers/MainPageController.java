package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.entity.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    @Autowired
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    CustomerDAO customerDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainPage() {

        Customer customer = customerDAO.findById(1);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("mainPage");
        return modelAndView;
    }
}
