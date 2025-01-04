package com.example.mobileapi.common;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Đang chờ xử lý"),
    SHIPPING("Đang giao hàng"),
    DELIVERED("Đã giao hàng"),
    CANCELLED("Đã hủy");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
