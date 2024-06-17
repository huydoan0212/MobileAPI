package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    int saveOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO getOrder(int orderId);
    void deleteOrder(int orderId);
    void updateOrder(int id, OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getOrderByCustomerId(int customerId);
    List<OrderResponseDTO> getAllOrders();
}
