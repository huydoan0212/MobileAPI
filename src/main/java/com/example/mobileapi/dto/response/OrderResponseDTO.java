package com.example.mobileapi.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Getter
public class OrderResponseDTO {
    private Integer id;
    private Integer customerId;
    private LocalDateTime orderDate;
    private Integer totalAmount;
    private String address;
    private String numberPhone;
    private String status;
    private String receiver;
    private Set<OrderDetailResponseDTO> orderDetails;
}
