package com.example.spring_data_jdbc_try.order.repository;

import io.vavr.control.Try;

public interface CustomOrderRepository {
    public Try<Boolean> tryUpdateStatus(Integer orderId, String newStatus);
    public Boolean updateStatus(Integer orderId, String newStatus);
}
