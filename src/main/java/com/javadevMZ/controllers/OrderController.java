package com.javadevMZ.controllers;

import com.javadevMZ.dao.*;
import com.javadevMZ.service.OrderManager;
import com.javadevMZ.service.ProductManager;
import com.javadevMZ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/new_order")
    public ModelAndView newOrder() {
        Map<Product, OrderItem> items = new HashMap<Product, OrderItem>();
        Order order = orderManager.getCurrentOrder();
        if(order!=null && order.getItems()!=null) {
            items = order.getItems();
        }
        ModelAndView modelAndView = new ModelAndView("new_order");
        modelAndView.addObject("items", items);
        modelAndView.addObject("isSenior", userService.getAuthorities().contains("ROLE_SENIOR_CASHIER"));
        return modelAndView;
    }

    @PostMapping("/new_order")
    public String changeOrder(@RequestParam String method,
                              @RequestParam(required = false) String nameOrId,
                              @RequestParam(required = false) Long quantity) {
      if(nameOrId!=null && quantity!=null) {
          Product product = productManager.getProductByNameOrId(nameOrId);
          if (method.equals("add")) {
              orderManager.addItem(product, quantity);
          }
          if (method.equals("edit_item")) {
              orderManager.editItem(product, quantity);
          }
      }

      if(method.equals("remove_item")) {
          orderManager.removeOrderItem(nameOrId);
      }
          if (method.equals("cancel")) {
              orderManager.cancelOrder();
              return "redirect:/";
          }

          if(method.equals("submit")) {
            orderManager.commitOrder();
            return "redirect:/";
          }
        return "redirect:/new_order";
    }

}
