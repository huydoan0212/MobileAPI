package com.example.mobileapi.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
public class OrderRequestDTO {
    private Integer customerId;
    private Integer totalAmount;
    private String address;
    private String numberPhone;
    private String status;
    private String receiver;
    private List<OrderDetailRequestDTO> orderDetails;
}

