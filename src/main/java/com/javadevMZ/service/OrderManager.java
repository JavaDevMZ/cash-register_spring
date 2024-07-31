package com.javadevMZ.service;

import com.javadevMZ.dao.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OrderManager {

    @Getter
    @Setter
    private Order currentOrder;
 
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductManager orderManager;
    @Autowired
    private UserService userService;
    
    public void editItem(Product product, Long quantity){
        currentOrder.getItems().put(product, new OrderItem(product, quantity));
    }

    @PreAuthorize("hasRole('SENIOR_CASHIER')")
    public void removeOrderItem(String productNameOrId){
        Map<Product, OrderItem> items = currentOrder.getItems();
        for(Map.Entry<Product, OrderItem> entry : items.entrySet()){
            if(entry.getKey().equals(orderManager.getProductByNameOrId(productNameOrId))){
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

    public void addItem(Product product, Long quantity){
        if(currentOrder == null){currentOrder = new Order();}
        if(quantity <= 0 || quantity > product.getQuantity()){throw new IllegalArgumentException("Wrong quantity!");}
        currentOrder.addItem(new OrderItem(product, quantity));
    }
}
