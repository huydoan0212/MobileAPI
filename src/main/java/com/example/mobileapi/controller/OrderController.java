package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.OrderRequestDTO;
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
    public int saveOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.saveOrder(orderRequestDTO);
    }
    @GetMapping("/customer/{customerId}")
    public List<OrderResponseDTO> getOrderByCustomerId(@PathVariable int customerId){
        return orderService.getOrderByCustomerId(customerId);
    }
    @GetMapping("/list")
    public List<OrderResponseDTO> getAllOrders(){
        return orderService.getAllOrders();
    }
}
