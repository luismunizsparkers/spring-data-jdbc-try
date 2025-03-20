package com.example.spring_data_jdbc_try.order.repository;

import io.vavr.control.Try;

/*
 * Created by luis.muniz on 2025-03-14
 */
public interface CustomOrderRepository {
    public Try<Boolean> tryUpdateStatus(Integer orderId, String newStatus);
    public Boolean updateStatus(Integer orderId, String newStatus);
}
