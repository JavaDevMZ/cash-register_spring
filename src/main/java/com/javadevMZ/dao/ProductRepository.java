package com.javadevMZ.dao;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.net.URL;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.name = :name, p.price = :price," +
            "p.imageLink = :imageLink, p.quantity = :quantity, p.description = :description" +
            " WHERE p.id=:id")
    void update(Long id, String name, URL imageLink, Double price, Long quantity, String description);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = :quantity WHERE p.id=:id")
    void update(Long id, Long quantity);


    Product findByName(String name);
}
