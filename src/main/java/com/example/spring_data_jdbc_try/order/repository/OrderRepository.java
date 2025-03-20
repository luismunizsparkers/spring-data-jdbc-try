package com.example.spring_data_jdbc_try.order.repository;

import com.example.spring_data_jdbc_try.order.data.Order;
import io.vavr.control.Try;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Integer>, CustomOrderRepository {
    @Modifying
    @Query("UPDATE orders SET status = :newStatus WHERE id = :orderId")
    public Try<Boolean> tryUpdateStatus2(Integer orderId, String newStatus);
}
