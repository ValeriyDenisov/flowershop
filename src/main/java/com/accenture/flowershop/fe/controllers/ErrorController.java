package com.accenture.flowershop.fe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController extends AbstractController{

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(@RequestParam(required = false) String msg, Model model) {
        if (!StringUtils.isEmpty(msg)) {
            model.addAttribute("msg", msg);
        }
        return "errorPage";
    }
}
