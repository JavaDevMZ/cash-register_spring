package com.javadevMZ.dao;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_items")
public class OrderItem extends AbstractEntity<Long>{

    @ManyToOne
    @JoinColumn(name= "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    private Product product;
    private Long quantity;

    public OrderItem(Long id, Product product, Order order, Long quantity) {
        setId(id);
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderItem(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setQuantity(Long quantity){
        if(quantity<product.getQuantity() && quantity>=0){
           this.quantity = quantity;
        }else{
            throw new IllegalArgumentException("Illegal quantity!");
        }
    }

    public Double getTotalAmount(){
        return product.getPrice()*quantity;
    }
}
