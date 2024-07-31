package com.javadevMZ.controllers;

import com.javadevMZ.dao.Order;
import com.javadevMZ.service.ReportManager;
import com.javadevMZ.service.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportManager reportManager;

    @GetMapping("/x-report")
    public ModelAndView xReport(ModelAndView mav){
        List<String> orders = reportManager.getOrdersAsStrings();
       mav.addObject("today_orders", reportManager.getOrdersAsStrings());

       mav.setViewName("x-report");
       return mav;
    }

    @GetMapping("/z-report")
    public ModelAndView zReport(ModelAndView mav){
        mav.addObject("order_number", reportManager.getTodayOrders().size());
        mav.addObject("income", reportManager.getTodayIncome());
        mav.setViewName("z-report");
        return mav;
    }
}
