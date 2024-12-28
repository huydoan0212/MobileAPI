package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CreateAddressDto;
import com.example.mobileapi.dto.request.UpdateAddressDto;
import com.example.mobileapi.model.Address;

import java.util.List;

public interface AddressService {

    Address createAddress(CreateAddressDto dto);

    Address updateAddress(Integer id, UpdateAddressDto dto);

    void deleteAddress(Integer id);

    Address getAddress(Integer id);

    List<Address> getAddressByCustomerId(Integer customerId);

    List<Address> getAllAddress();

}
