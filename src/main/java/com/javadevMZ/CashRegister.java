package com.javadevMZ;

import com.javadevMZ.dao.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class CashRegister {


    public static void main(String[] args) {

        SpringApplication.run(CashRegister.class, args);
    }
}
