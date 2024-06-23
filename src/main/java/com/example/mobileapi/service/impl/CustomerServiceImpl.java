package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public int saveCustomer(CustomerRequestDTO request) {
        Customer customer = Customer.builder().
                fullname(request.getFullname()).
                username(request.getUsername()).
                password(request.getPassword()).
                email(request.getEmail()).
                phone(request.getPhone()).
                build();
        customerRepository.save(customer);
        return customer.getId();
    }

    @Override
    public void updateCustomer(int customerId, CustomerRequestDTO request) {
        Customer customer = getCustomerById(customerId);
        customer.setFullname(request.getFullname());
        customer.setUsername(request.getUsername());
        customer.setPassword(request.getPassword());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerRepository.deleteById(customerId);

    }

    @Override
    public CustomerResponseDTO getCustomer(int customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            return null;
        }
        return CustomerResponseDTO.builder()
                .username(customer.getUsername())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .fullname(customer.getFullname())
                .id(customer.getId())
                .role(customer.isRole())
                .build();
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseDTO> customerResponseDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponseDTOS.add(CustomerResponseDTO.builder()
                            .fullname(customer.getFullname())
                    .username(customer.getUsername())
                    .phone(customer.getPhone())
                    .email(customer.getEmail())
                    .id(customer.getId())
                            .role(customer.isRole())
                    .build());
        }
        return customerResponseDTOS;
    }

    @Override
    public boolean checkUsername(String username) {
        System.out.println(customerRepository.existsByUsername(username));
        return customerRepository.existsByUsername(username);
    }

    @Override
    public CustomerResponseDTO login(String username, String password) {
        Customer customer = customerRepository.login(username, password);
        return CustomerResponseDTO.builder()
                .fullname(customer.getFullname())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .id(customer.getId())
                .role(customer.isRole())
                .build();

    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}
