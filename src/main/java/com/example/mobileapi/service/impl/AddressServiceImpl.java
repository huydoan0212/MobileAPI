package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CreateAddressDto;
import com.example.mobileapi.dto.request.UpdateAddressDto;
import com.example.mobileapi.model.Address;
import com.example.mobileapi.repository.AddressRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressServiceImpl(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public Address createAddress(CreateAddressDto dto) {
        Address address = Address.builder()
                .address(dto.getAddress())
                .numberPhone(dto.getNumberPhone())
                .receiver(dto.getReceiver())
                .note(dto.getNote())
                .customer(customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found")))
                .build();
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Integer id, UpdateAddressDto dto) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found"));
        address.setAddress(dto.getAddress());
        address.setNumberPhone(dto.getNumberPhone());
        address.setReceiver(dto.getReceiver());
        address.setNote(dto.getNote());
        address.setCustomer(customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found")));
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address getAddress(Integer id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    public List<Address> getAddressByCustomerId(Integer customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }
}
