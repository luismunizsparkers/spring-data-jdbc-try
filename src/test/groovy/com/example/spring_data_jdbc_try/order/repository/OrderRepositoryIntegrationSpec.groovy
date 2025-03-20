package com.example.spring_data_jdbc_try.order.repository

import com.example.spring_data_jdbc_try.order.data.Order
import com.example.spring_data_jdbc_try.SpringDataJdbcTryApplication
import com.example.spring_data_jdbc_try.order.repository.OrderRepository
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import spock.lang.Subject

import javax.sql.DataSource


/*
 * Created by luis.muniz on 2025-03-19
 */

@Subject(OrderRepository)
@SpringBootTest(classes = [SpringDataJdbcTryApplication])
@Transactional
class OrderRepositoryIntegrationSpec extends Specification {
    @Subject
    @Autowired
    DataSource dataSource

    @Autowired
    private OrderRepository orderRepository

    @Autowired
    private ApplicationContext applicationContext

    def "It has an applicaiton context"() {
        given:
        println applicationContext.getBeanDefinitionNames().join("\n")

        expect:
        dataSource != null
    }

    def "It succeeds to update an order's status if the custom repository method returns a Boolean"() {
        given:
        def order = new Order(null, anyString(), "NEW")
        var saved = orderRepository.save(order)


        when:
        def newStatus = orderRepository.updateStatus(saved.id, "OUT_OF_STOCK")

        then:
        newStatus
    }

    def "It fails to correctly return the type of a CUSTOM repository method that returns a Try instance. Instead returns a Try<Try<?>>"() {
        given:
        def order = new Order(null, anyString(), "NEW")
        var saved = orderRepository.save(order)


        when:
        def attempt = orderRepository.tryUpdateStatus(saved.id(), "OUT_OF_STOCK")

        then:
        attempt.success
        Try.isAssignableFrom(attempt.class)
        Try<Boolean> wrappedTry = attempt.get() as Try// this should be a String, but is a Try
        Try.isAssignableFrom(wrappedTry.class)
        wrappedTry.success
        wrappedTry.get() == true
    }

    def "It correctly returns the type of a BASE repository method that returns a Try instance."() {
        given:
        def order = new Order(null, anyString(), "NEW")
        var saved = orderRepository.save(order)


        when:
        def attempt = orderRepository.tryUpdateStatus2(saved.id(), "OUT_OF_STOCK")

        then:
        attempt.success
        Try.isAssignableFrom(attempt.class)
        def wrapped = attempt.get()
        Boolean.isAssignableFrom(wrapped.class)
        wrapped == true
    }


    static String anyString() {
        UUID.randomUUID().toString()
    }
}
