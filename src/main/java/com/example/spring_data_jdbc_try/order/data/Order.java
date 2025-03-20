package com.example.spring_data_jdbc_try.order.data;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

/*
 * Created by luis.muniz on 2025-03-13
 */
@Table(name="ORDERS")
public record Order(@Id Integer id, String description, @NotBlank String status) {
}
