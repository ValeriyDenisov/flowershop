package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.api.exceptions.EntityCreationException;
import com.accenture.flowershop.be.api.service.FlowershopService;
import com.accenture.flowershop.fe.formdata.RegistrationFD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    public static final String ERROR_CONFIRM_PASSWORD = "Passwords does not same";

    @Autowired
    FlowershopService flowershopService;

    @RequestMapping(method = RequestMethod.GET)
    public String registrationPage(Model model) {
        RegistrationFD registration = new RegistrationFD();
        model.addAttribute("registration", registration);
        return "registrationPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerCustomer(@Valid @ModelAttribute("registration") RegistrationFD registration,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registration", registration);
            return "registrationPage";
        }
        if (!registration.getPassword().equals(registration.getConfirmPassword())) {
            registration.setError(ERROR_CONFIRM_PASSWORD);
            model.addAttribute("registration", registration);
            return "registrationPage";
        }

        try {
            flowershopService.customerRegistration(registration.getName(), registration.getSecondName(), registration.getFatherName(),
                    registration.getCity(), registration.getStreet(), registration.getCode(), registration.getBuilding(),
                    registration.getPhone(), registration.getEmail(), registration.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(registration.getEmail(),
                    registration.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (EntityCreationException e) {
            registration.setError(e.getMessage());
            model.addAttribute("registration", registration);
            return "registrationPage";
        }

        return "redirect:/";
    }
}
