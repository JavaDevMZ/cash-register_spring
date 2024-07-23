package com.javadevMZ.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="orders")
public class Order extends AbstractEntity<Long>{

    @MapKey(name = "product")
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    private Map<Product, OrderItem> items = new HashMap<>();

    private Date date;

    @ManyToOne
    private User cashier;

    public Order(Long id, Double amount, Map<Product, OrderItem> items, Date date, User cashier) {
        setId(id);
        this.items = items;
        this.date = date;
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
}
