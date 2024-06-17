package com.example.mobileapi.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponseDTO {
    private int id;

    private String username;

    private String email;

    private String phone;
}
