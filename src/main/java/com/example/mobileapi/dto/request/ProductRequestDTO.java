package com.example.mobileapi.dto.request;

import com.example.mobileapi.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class ProductRequestDTO implements Serializable {
    private String name;
    private String img;
    private int price;
    private int categoryId;
    private String detail;

}
