package com.javadevMZ.controllers;

import com.javadevMZ.service.ReportManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private final ReportManager reportManager;

    public ReportController(ReportManager reportManager) {
        this.reportManager = reportManager;
    }

    @GetMapping("/x-report")
    public String xReport(){
        return reportManager.getXReport();
    }

    @GetMapping("/z-report")
    public String zReport(){
        return reportManager.getZReport();
    }
}
