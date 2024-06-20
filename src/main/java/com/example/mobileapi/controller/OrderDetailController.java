package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.service.OrderDetailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-detail")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping
    public OrderDetailResponseDTO saveOrderDetail(OrderDetailRequestDTO orderDetailRequestDTO) {
        return orderDetailService.saveOrderDetail(orderDetailRequestDTO);
    }

    @PutMapping("/{id}")
    public OrderDetailResponseDTO updateOrderDetail(@PathVariable int id, OrderDetailRequestDTO orderDetailRequestDTO) {
        return orderDetailService.updateOrderDetail(id, orderDetailRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable int id) {
        orderDetailService.deleteOrderDetail(id);
    }

    @GetMapping("/id/{id}")
    public OrderDetailResponseDTO getOrderDetailById(@PathVariable int id) {
        return orderDetailService.findOrderDetailById(id);
    }

    @GetMapping("/orderId/{orderId}")
    public OrderDetailResponseDTO getOrderDetailByOrderId(@PathVariable int orderId) {
        return orderDetailService.findOrderDetailByOrderId(orderId);
    }

    @GetMapping("/productId/{productId}")
    public OrderDetailResponseDTO getOrderDetailByProductId(@PathVariable int productId) {
        return orderDetailService.findOrderDetailByProductId(productId);
    }
}
