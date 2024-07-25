package com.javadevMZ.dao;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="orders")
public class Order extends AbstractEntity<Long>{

    @MapKey(name = "product")
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    private Map<Product, OrderItem> items = new HashMap<>();

    @Column(nullable=false)
    private LocalDateTime closedAt;

    @ManyToOne
    private User cashier;

    public Order(Long id, Double amount, Map<Product, OrderItem> items, LocalDateTime closedAt, User cashier) {
        setId(id);
        this.items = items;
        this.closedAt = closedAt;
        this.cashier = cashier;
    }

    public void addItem(OrderItem item){
        Product product = item.getProduct();
        if(items.containsKey(product)){
            items.get(product).setQuantity(items.get(product).getQuantity()+item.getQuantity());
        }else {
            items.put(product, item);
        }
        item.setOrder(this);
    }

    public Double getAmount(){
       Double result = 0.0;
       for(OrderItem item : items.values()){
           result+=item.getTotalAmount();
       }
        return result;
    }

    public String toString(){
        return String.format("Order N°%d: closed by %s %s at %tT. Amount: %f$",
                getId(), cashier.getRole().toString(), cashier.getEmail(), closedAt, getAmount());
    }
}
