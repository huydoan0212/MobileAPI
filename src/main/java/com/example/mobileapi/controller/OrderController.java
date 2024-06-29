package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.OrderEditRequestDTO;
import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public int saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.saveOrder(orderRequestDTO);
    }

    @PutMapping("/order/{orderId}")
    public void editOrder(@RequestBody OrderEditRequestDTO orderRequestDTO, @PathVariable("orderId") int orderId) {
        orderService.editOrder(orderId, orderRequestDTO);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderResponseDTO> getOrderByCustomerId(@PathVariable int customerId) {
        return orderService.getOrderByCustomerId(customerId);
    }

    @GetMapping("/list")
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") int orderId) {
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/revenue")
    public List<MonthlyRevenueResponse> getOrderRevenue() {
        return orderService.getMonthlyRevenue();
    }

    @GetMapping("/{status}")
    public List<OrderResponseDTO> getOrderByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }
}
