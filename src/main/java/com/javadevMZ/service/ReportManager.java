package com.javadevMZ.service;

import com.javadevMZ.dao.Order;
import com.javadevMZ.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportManager {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MessageSource messageSource;

    public List<Order> getTodayOrders(){
        return orderRepository.findAllByClosedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
    }

    public List<String> getOrdersAsStrings(){
        List<String> ordersAsStrings = new ArrayList<>();
        for(Order order : getTodayOrders()){
            Object[] args = {order.getId(), order.getCashier().getRole(), order.getCashier().getEmail(), order.getClosedAt().toLocalTime(), order.getAmount()};
            ordersAsStrings.add(messageSource.getMessage("report.order.to_string",
                    args,
                    LocaleContextHolder.getLocale()));
        }
        return ordersAsStrings;
    }

   public Double getTodayIncome(){
        Double todayIncome = 0.0;
        for(Order order : getTodayOrders()){
            todayIncome +=order.getAmount();
        }
        return todayIncome;
   }
}
