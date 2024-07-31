package com.javadevMZ.service;

import com.javadevMZ.dao.*;
import lombok.Data;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.LocaleResolver;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Data
@Service
@EnableMethodSecurity
@SessionScope
public class ProductManager {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private Translator translator;
    @Autowired
    private LocaleResolver localeResolver;
    @Autowired
    private Manager manager;

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

    public void translateProduct(Product product, Locale origin){
        product.setName(translator.translate(product.getName(), origin, Locale.ENGLISH));
        product.setDescription(translator.translate(product.getDescription(), origin, Locale.ENGLISH));
    }

}
