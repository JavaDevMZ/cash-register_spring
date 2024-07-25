package com.javadevMZ.service;

import com.javadevMZ.dao.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Service
@EnableMethodSecurity
@SessionScope
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

    @PreAuthorize("hasRole('SENIOR_CASHIER')")
    public void removeOrderItem(String productNameOrId){
        Map<Product, OrderItem> items = currentOrder.getItems();
        for(Map.Entry<Product, OrderItem> entry : items.entrySet()){
            if(entry.getKey().equals(getProductByNameOrId(productNameOrId))){
                items.remove(entry.getKey());
            }
        }
    }

    public void commitOrder(){
        for(Product product : currentOrder.getItems().keySet()){
          Long quantity = product.getQuantity()-currentOrder.getItems().get(product).getQuantity();
            productRepository.findById(product.getId()).orElseThrow().setQuantity(quantity);
        }
        currentOrder.setCashier(userService.getCurrentUser());
        currentOrder.setClosedAt(LocalDateTime.now());
        orderRepository.save(currentOrder);
        currentOrder = null;
    }

    @PreAuthorize("hasRole('SENIOR_CASHIER')")
    public void cancelOrder(){
        currentOrder = null;
    }
}
