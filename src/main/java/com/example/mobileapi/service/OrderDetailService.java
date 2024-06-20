package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.OrderDetailRequestDTO;
import com.example.mobileapi.dto.response.OrderDetailResponseDTO;
import com.example.mobileapi.model.OrderDetail;

public interface OrderDetailService {
    OrderDetailResponseDTO saveOrderDetail(OrderDetailRequestDTO requestDTO);

    OrderDetailResponseDTO updateOrderDetail(int id, OrderDetailRequestDTO requestDTO);

    void deleteOrderDetail(int id);

    OrderDetailResponseDTO findOrderDetailById(int id);

    OrderDetailResponseDTO findOrderDetailByOrderId(int orderId);

    OrderDetailResponseDTO findOrderDetailByProductId(int productId);


}
