package com.javadevMZ.service;

import com.javadevMZ.dao.Order;
import com.javadevMZ.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ReportManager {

    @Autowired
    private OrderRepository orderRepository;

    public String getXReport(){
        String result = new String();
        Iterable<Order> orders = orderRepository.findAllByClosedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        for(Order order : orders){
                result+=order.toString() + "<br>";
        }
        return result;
    }

    public String getZReport(){
        double totalAmount = 0.0;
        Iterable<Order> orders = orderRepository.findAllByClosedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        for(Order order : orders){
          totalAmount += order.getAmount();
        }
        return "Total Orders:" + orderRepository.count()+"<br>Total income: " + totalAmount;
    }
}
