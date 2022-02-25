package com.customer.web.controllers;

import com.customer.web.services.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @Autowired
    private VersionService versionService;

    @GetMapping
    @RequestMapping("/version")
    public String getVersion() {
        return versionService.getVersion();
    }
}
