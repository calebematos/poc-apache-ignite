package com.calebematos.pocapacheignite.controller;

import com.calebematos.pocapacheignite.service.TestService;
import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService service;
    @PostMapping
    public String test(@RequestBody String name) throws InterruptedException {

        service.saveInCache(name);

        return "it Worked";
    }

}
