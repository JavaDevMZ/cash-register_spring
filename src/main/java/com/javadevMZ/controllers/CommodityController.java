package com.javadevMZ.controllers;

import com.javadevMZ.dao.Product;
import com.javadevMZ.dao.ProductRepository;
import com.javadevMZ.service.ProductManager;
import com.javadevMZ.service.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;

@Controller
public class CommodityController {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private LocaleResolver localeResolver;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Translator translator;

    @GetMapping("/products")
    @ResponseBody
    public String listProducts(){
        Iterable<Product> products = repository.findAll();
        String result = "<h1>Products in the warehouse: </br>";
        for(Product product : products){
            result += String.format("<p><a href=/products/%d>%s</a></br>",
                    product.getId(),
                    product.toString()
            );
        }
        return translator.translate(result, Locale.ENGLISH, LocaleContextHolder.getLocale());
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
           try {
               productManager.translateProduct(product, LocaleContextHolder.getLocale());
           }catch (Exception e){}
           productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public ModelAndView showProduct(ModelAndView modelAndView, @PathVariable Long id){
        Product product = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        modelAndView.addObject("product_id", product.getId());
        modelAndView.addObject("product_name", translator.translate(product.getName(), Locale.ENGLISH, LocaleContextHolder.getLocale()));
        modelAndView.addObject("image_link", product.getImageLink());
        modelAndView.addObject("price", product.getPrice());
        modelAndView.addObject("quantity", product.getQuantity());
        modelAndView.addObject("description", translator.translate(product.getDescription(), Locale.ENGLISH, LocaleContextHolder.getLocale()));
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
    productManager.translateProduct(product, LocaleContextHolder.getLocale());
      if(method.equalsIgnoreCase("update")){
          updateProduct(product);
      }
      return "redirect:/products/"+id;
    }

    public void updateProduct(Product product){
        repository.update(product.getId(), product.getName(), product.getImageLink(),
                product.getPrice(), product.getQuantity(), product.getDescription());
    }
}