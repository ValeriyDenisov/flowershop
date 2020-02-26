package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.api.service.CustomerService;
import com.accenture.flowershop.be.api.service.FlowerService;
import com.accenture.flowershop.be.api.service.FlowershopService;
import com.accenture.flowershop.be.api.service.OrderService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.fe.application.Cart;
import com.accenture.flowershop.fe.dto.converter.FlowerDTOConverter;
import com.accenture.flowershop.fe.dto.converter.OrderDTOConverter;
import com.accenture.flowershop.fe.dto.entity.FlowerDTO;
import com.accenture.flowershop.fe.dto.entity.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes(value = "cart")
public class MainPageController extends AbstractController {

    @Autowired
    FlowerService flowerService;

    @Autowired
    CustomerService customerService;

    @Autowired
    FlowershopService flowershopService;

    @Autowired
    FlowerDTOConverter flowerDTOConverter;

    @Autowired
    OrderDTOConverter orderDTOConverter;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(@RequestParam(required = false) Double priceFrom, @RequestParam(required = false) Double priceTo,
                           HttpSession session, Principal principal, Model model) {
        if (isUser(principal)) {
            String username = principal.getName();
            List<FlowerDTO> flowers = flowerDTOConverter.convertAll(flowerService.findFlowersByPrice(priceFrom, priceTo,
                    null, null));
            List<OrderDTO> orders = orderDTOConverter.convertAll(orderService.findOrdersByCustomerEmail(username));
            model.addAttribute("flowers", flowers);
            model.addAttribute("orders", orders);
        }
        return "mainPage";
    }

    @RequestMapping(value = "/addFlower", method = RequestMethod.POST)
    public String addFlowerToCart(@RequestParam Integer count, @RequestParam String flowerName, HttpSession session,
                                  Principal principal, Model model) {
        if (count == null || StringUtils.isEmpty(flowerName)) {
            return REDIRECT + "/";
        }
        if (isUser(principal)) {
            Cart cart = (Cart) session.getAttribute("cart");
            Customer customer = customerService.findCustomerByEmail(principal.getName());
            if (customer == null) {
                return "errorPage";
            }

            cart = flowershopService.addFlowersToCart(cart, flowerName, count, customer.getDiscount());
            session.setAttribute("cart", cart);
            return REDIRECT + "/";
        } else {
            return "errorPage";
        }
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(@RequestParam Double price, SessionStatus sessionStatus, Principal principal) {
        if(isUser(principal)) {
            orderService.createOrder(price, principal.getName());
            sessionStatus.setComplete();
            return REDIRECT + "/";
        } else {
            return "errorPage";
        }
    }

    @RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
    public String closeOrder(@RequestParam Integer id, Principal principal, Model model) {
        if (isUser(principal)) {
            orderService.closeOrder(id);
            return REDIRECT + "/";
        } else {
            return "errorPage";
        }
    }
}


