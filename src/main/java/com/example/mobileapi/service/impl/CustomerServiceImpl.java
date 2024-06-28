package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.request.CustomerUpdateRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EmailService emailService;

    @Override
    public int saveCustomer(CustomerRequestDTO request) {
        Customer customer = Customer.builder()
                .fullname(request.getFullname())
                .username(request.getUsername())
                .password(hashPassword(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
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

    @Override
    public CustomerResponseDTO updateCustomerById(int customerId, CustomerUpdateRequestDTO request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Khong tim thay customer"));
        customer.setFullname(request.getName());
        customer.setPassword(request.getPassword());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
        return CustomerResponseDTO.builder()
                .fullname(customer.getFullname())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .id(customer.getId())
                .role(customer.isRole())
                .build();
    }

    @Override
    public void updateByAdmin(int customerId, CustomerRequestDTO request) {
        Customer customer = getCustomerById(customerId);
        customer.setFullname(request.getFullname());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
    }

    @Override
    public void resetPassword(String username, String resetCode, String newPassword) {
        Customer customer = getCustomerByName(username);
        if (customer != null && resetCode.equals(customer.getResetCode())) {
            customer.setPassword(hashPassword(newPassword));
            customer.setResetCode(null); // Xóa mã reset sau khi đặt lại mật khẩu thành công
            customerRepository.save(customer);
            emailService.sendPasswordResetEmail(
                    customer.getEmail(),
                    "Xác nhận Đặt Lại Mật Khẩu",
                    "Mật khẩu của bạn đã được đặt lại thành công."
            );
        } else {
            throw new IllegalArgumentException("Mã reset không hợp lệ hoặc đã hết hạn.");
        }
    }

    @Override
    public void initPasswordReset(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            String resetCode = generateResetCode();
            customer.setResetCode(resetCode);
            customerRepository.save(customer);

            // Gửi email chứa mã resetCode đến email của khách hàng
            emailService.sendPasswordResetEmail(
                    customer.getEmail(),
                    "Yêu cầu Đặt Lại Mật Khẩu",
                    "Mã xác nhận đặt lại mật khẩu của bạn là: " + resetCode
            );
        } else {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với username: " + username);
        }
    }
    Customer getCustomerByName(String username) {
        return customerRepository.findByUsername(username);
    }

    private String generateResetCode() {
        // Tạo một mã ngẫu nhiên chứa các ký tự chữ và số
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}
