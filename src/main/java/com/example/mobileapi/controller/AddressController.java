package com.example.mobileapi.controller;

import com.example.mobileapi.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private AddressService addressService;
    @GetMapping("")
    public ResponseEntity<String> getAllAddress() {
        return ResponseEntity.ok(addressService.getAllAddress().toString());
    }
}
