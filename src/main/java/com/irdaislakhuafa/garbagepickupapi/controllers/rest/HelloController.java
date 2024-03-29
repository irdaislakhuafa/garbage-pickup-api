package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/restapi/hello"})
public class HelloController {
    @RequestMapping
    public ResponseEntity<Map<String, Object>> hello() {
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("message", "hello world");
            }
        });
    }
}