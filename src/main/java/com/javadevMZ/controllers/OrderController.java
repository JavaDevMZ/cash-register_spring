package com.javadevMZ.controllers;

import com.javadevMZ.dao.OrderItem;
import com.javadevMZ.dao.UserRepository;
import com.javadevMZ.service.ProductManager;
import com.javadevMZ.dao.Order;
import com.javadevMZ.dao.Product;
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
    private ProductManager productManager;
    @Autowired
    private UserService userService;


    @GetMapping("/new_order")
    public ModelAndView newOrder() {
        Map<Product, OrderItem> items = new HashMap<Product, OrderItem>();
        Order order = productManager.getCurrentOrder();
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
              productManager.addItem(product, quantity);
          }
          if (method.equals("edit_item")) {
              productManager.editItem(product, quantity);
          }
      }
          if (method.equals("delete")) {

          }
        if(method.equals("submit")) {
            productManager.commitOrder();
        }
        return "redirect:/new_order";
    }
}
