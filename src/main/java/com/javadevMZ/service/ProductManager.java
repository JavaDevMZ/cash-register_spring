package com.javadevMZ.service;

import com.javadevMZ.dao.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@Service
@EnableMethodSecurity
public class ProductManager {

    private Order currentOrder;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    public void addItem(Product product, Long quantity){
        if(currentOrder == null){currentOrder = new Order();}
        if(quantity <= 0 || quantity > product.getQuantity()){throw new IllegalArgumentException("Wrong quantity!");}
        currentOrder.addItem(new OrderItem(product, quantity));
    }

    public Product getProductByNameOrId(String nameOrId){
      Product product = null;
        try{
           Long id = Long.parseLong(nameOrId);
           product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        }catch(IllegalArgumentException e){
           product = productRepository.findByName(nameOrId);
        }
        return product;
    }

    public void editItem(Product product, Long quantity){
        currentOrder.getItems().put(product, new OrderItem(product, quantity));
    }

    public void commitOrder(){
        for(Product product : currentOrder.getItems().keySet()){
          Long quantity = product.getQuantity()-currentOrder.getItems().get(product).getQuantity();
            productRepository.findById(product.getId()).orElseThrow().setQuantity(quantity);
        }
        currentOrder.setCashier(userService.getCurrentUser());
        currentOrder.setDate(new Date());
        orderRepository.save(currentOrder);
        currentOrder = null;
    }
}
