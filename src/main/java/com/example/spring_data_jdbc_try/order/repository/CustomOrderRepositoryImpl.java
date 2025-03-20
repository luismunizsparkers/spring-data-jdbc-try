package com.example.spring_data_jdbc_try.order.repository;

import io.vavr.control.Try;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomOrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean updateStatus(Integer orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        var updated = jdbcTemplate.update(sql, newStatus, orderId);
        if (updated == 0) {
            throw new RuntimeException("Order " + orderId + " not found");
        } else {
            return true;
        }
    }

    public Try<Boolean> tryUpdateStatus(Integer orderId, String newStatus) {
        return Try.of(() -> updateStatus(orderId, newStatus));
    }
}
