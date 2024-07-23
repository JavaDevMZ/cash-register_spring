package com.javadevMZ.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.Id;
import lombok.experimental.SuperBuilder;

import java.net.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product extends AbstractEntity<Long>{

    private String name;
    private URL imageLink;
    private double price;
    private Long quantity;
    private String description;

    public Product(Long id, String name, String description, Long quantity, double price) {
        setId(id);
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return getName() + " Price: " + getPrice() + " Quantity: " + getQuantity();
    }
}
