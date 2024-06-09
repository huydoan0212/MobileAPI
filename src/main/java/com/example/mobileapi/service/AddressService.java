package com.example.mobileapi.service;

import com.example.mobileapi.model.Address;
import com.example.mobileapi.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private AddressRepository repository;

    public List<Address> getAllAddress() {
        return repository.findAll();
    }
}
