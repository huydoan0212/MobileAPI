package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.model.Order;
import com.example.mobileapi.model.OrderDetail;
import com.example.mobileapi.model.Product;
import com.example.mobileapi.repository.OrderDetailRepository;
import com.example.mobileapi.repository.OrderRepository;
import com.example.mobileapi.repository.ProductRepository;
import com.example.mobileapi.service.OrderDetailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    public OrderDetailResponseDTO saveOrderDetail(OrderDetailRequestDTO requestDTO) {
        Order order = orderRepository.findById(requestDTO.getOrderId()).orElse(null);
        Product product = productRepository.findById(requestDTO.getProductId()).orElse(null);
        orderDetailRepository.save(OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(requestDTO.getQuantity())
                .build());

        return OrderDetailResponseDTO.builder()
                .orderId(order.getId())
                .productId(product.getId())
                .quantity(requestDTO.getQuantity())
                .build();
    }

    @Override
    public OrderDetailResponseDTO updateOrderDetail(int id, OrderDetailRequestDTO requestDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        Order order = orderRepository.findById(requestDTO.getOrderId()).orElse(null);
        Product product = productRepository.findById(requestDTO.getProductId()).orElse(null);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(requestDTO.getQuantity());
        return OrderDetailResponseDTO.builder()
                .orderId(order.getId())
                .productId(product.getId())
                .quantity(requestDTO.getQuantity())
                .build();
    }

    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailById(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        return OrderDetailResponseDTO.builder()
                .productId(orderDetail.getProduct().getId())
                .orderId(orderDetail.getOrder().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailByOrderId(int orderId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderByOrderId(orderId);
        return OrderDetailResponseDTO.builder()
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }

    @Override
    public OrderDetailResponseDTO findOrderDetailByProductId(int productId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderByProductId(productId);
        return OrderDetailResponseDTO.builder()
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
