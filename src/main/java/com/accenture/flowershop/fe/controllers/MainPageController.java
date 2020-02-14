package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.api.dao.CustomerDAO;
import com.accenture.flowershop.be.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        return new ModelAndView();
    }
}
