package com.javadevMZ.controllers;

import com.javadevMZ.dao.Product;
import com.javadevMZ.dao.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;

@Controller
public class CommodityController {

   // @PersistenceContext
    //private EntityManager manager;
    @Autowired
    private ProductRepository repository;

    @GetMapping("/products")
    @ResponseBody
    public String listProducts(){
        Iterable<Product> products = repository.findAll();
        String result = "<h1>Products in the warehouse: </br>";
        for(Product product : products){
            result += String.format("<p><a href=/products/%d>%s</a></br>", product.getId(), product.toString());
        }
        return result;
    }

    @GetMapping("/new_product")
    public String newProduct(){
        return "new_product";
    }

    @PostMapping(value = "/products")
    public String addProduct(@RequestParam String productName, @RequestParam Double price,
                             @RequestParam Long quantity, @RequestParam String description,
                             @RequestParam URL imageLink){

        Product product = new Product(productName, imageLink, price, quantity, description);
        repository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public ModelAndView showProduct(ModelAndView modelAndView, @PathVariable Long id){
        Product product = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        modelAndView.addObject("product_id", product.getId());
        modelAndView.addObject("product_name", product.getName());
        modelAndView.addObject("image_link", product.getImageLink());
        modelAndView.addObject("price", product.getPrice());
        modelAndView.addObject("quantity", product.getQuantity());
        modelAndView.addObject("description", product.getDescription());
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam("method") String method,
                              @RequestParam String name, @RequestParam URL imageLink, @RequestParam Double price,
                                @RequestParam Long quantity, @RequestParam String description){
    Product product = Product.builder()
            .name(name)
            .price(price)
            .quantity(quantity)
            .description(description)
            .imageLink(imageLink)
            .build();
    product.setId(id);
      if(method.equalsIgnoreCase("update")){
          updateProduct(product);
      }
      return "redirect:/products/"+id;
    }

    public void updateProduct(Product product){
        repository.update(product.getId(), product.getName(), product.getImageLink(),
                product.getPrice(), product.getQuantity(), product.getDescription());
      //  manager.refresh(product);
    }
}