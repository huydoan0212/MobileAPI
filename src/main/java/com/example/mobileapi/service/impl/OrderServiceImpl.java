package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.OrderRequestDTO;
import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.model.Order;
import com.example.mobileapi.model.OrderDetail;
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.CustomerService;
import com.example.mobileapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;
    @Override
    public int saveOrder(OrderRequestDTO orderRequestDTO) {
        Order order = Order.builder()
                .customer(customerRepository.findById(orderRequestDTO.getCustomerId()).orElse(null))
                .orderDate(LocalDateTime.now())
                .total_mount(orderRequestDTO.getTotalAmount())
                .address(orderRequestDTO.getAddress())
                .numberPhone(orderRequestDTO.getNumberPhone())
                .status(orderRequestDTO.getStatus())
                .orderDetails(orderRequestDTO.getOrderDetails().stream()
                        .map(this::convertToOrderDetailEntity)
                        .collect(Collectors.toSet()))
                .build();

        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));

        return orderRepository.save(order).getId();
    }

    @Override
    public OrderResponseDTO getOrder(int orderId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            return null;
        }

        return convertToOrderResponseDTO(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void updateOrder(int id, OrderRequestDTO orderRequestDTO) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setTotal_mount(orderRequestDTO.getTotalAmount());
            order.setAddress(orderRequestDTO.getAddress());
            order.setNumberPhone(orderRequestDTO.getNumberPhone());
            order.setStatus(orderRequestDTO.getStatus());
            order.setOrderDetails(orderRequestDTO.getOrderDetails().stream()
                    .map(this::convertToOrderDetailEntity)
                    .collect(Collectors.toSet()));
            order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderResponseDTO> getOrderByCustomerId(int customerId) {
        return getOrdersByCustomerId(customerId);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no orders found
        }

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(int customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no orders found
        }

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    private Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        Set<OrderDetailResponseDTO> orderDetailDTOs = order.getOrderDetails().stream()
                .map(this::convertToOrderDetailResponseDTO)
                .collect(Collectors.toSet());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerDTO(customerService.getCustomer(order.getCustomer().getId()))
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotal_mount()) // Ensure this matches your field in OrderResponseDTO
                .address(order.getAddress())
                .numberPhone(order.getNumberPhone())
                .status(order.getStatus())
                .orderDetails(orderDetailDTOs)
                .build();
    }


    private OrderDetailResponseDTO convertToOrderDetailResponseDTO(OrderDetail orderDetail) {
        return OrderDetailResponseDTO.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productResponseDTO(productService.getProductById(orderDetail.getProduct().getId()))
                .quantity(orderDetail.getQuantity())
                .build();
    }

    private OrderDetail convertToOrderDetailEntity(OrderDetailRequestDTO dto) {
        return OrderDetail.builder()
                .product(productService.getById(dto.getProductId()))
                .quantity(dto.getQuantity())
                .build();
    }
}
