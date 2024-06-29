package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.CartRequestDTO;
import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.CustomerUpdateRequestDTO;
import com.example.mobileapi.dto.request.LoginRequest;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.ResponseData;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.service.CartService;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.impl.CustomerServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CartService cartService;

    @PostMapping
    public int addCustomer(@RequestBody CustomerRequestDTO customer) {
        if (customerService.checkUsername(customer.getUsername())) {
            return -1;
        }
        int userId = customerService.saveCustomer(customer);
        CartRequestDTO cartRequestDTO = new CartRequestDTO();
        cartRequestDTO.setCustomerId(userId);
        cartService.saveCart(cartRequestDTO);
        return userId;
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) {
        customerService.updateCustomer(customerId, customer);
    }

    @PutMapping("/admin/{customerId}")
    public void updateAdmin(@PathVariable int customerId, @RequestBody CustomerRequestDTO customer) {
        customerService.updateByAdmin(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
    }

    @GetMapping("/{customerId}")
    public CustomerResponseDTO getCustomer(@PathVariable int customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/list")
    public List<CustomerResponseDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/checkUsername/{username}")
    public boolean checkUsername(@PathVariable String username) {
        return customerService.checkUsername(username);
    }

    @PostMapping("/login")
    public CustomerResponseDTO login(@RequestBody LoginRequest loginRequest) {
        CustomerResponseDTO cus = customerService.login(loginRequest.getUsername(), loginRequest.getPassword());
        cus.setCartId(cartService.getCartByCustomerId(cus.getId()).getId());
        return cus;
    }

    @PostMapping("/resetPassword/{username}")

    public void resetPassword(
            @PathVariable String username,
            @RequestParam String resetCode,
            @RequestParam String newPassword) {
        customerService.resetPassword(username, resetCode, newPassword);
    }


    @PostMapping("/initPasswordReset/{username}")
    public void initPasswordReset(@PathVariable String username) {
        customerService.initPasswordReset(username);
    }

    @PutMapping("/updateByUser/{id}")
    public CustomerResponseDTO updateByUser(@PathVariable int id, @RequestBody CustomerUpdateRequestDTO customer) {
        return customerService.updateCustomerById(id, customer);
    }
}

