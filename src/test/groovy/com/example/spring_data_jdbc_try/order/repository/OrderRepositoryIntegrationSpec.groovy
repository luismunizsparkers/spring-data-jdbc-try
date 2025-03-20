package com.example.spring_data_jdbc_try.order.repository

import com.example.spring_data_jdbc_try.SpringDataJdbcTryApplication
import com.example.spring_data_jdbc_try.order.data.Order
import io.vavr.control.Try
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import spock.lang.Subject

@Subject(OrderRepository)
@SpringBootTest(classes = [SpringDataJdbcTryApplication])
@Transactional
class OrderRepositoryIntegrationSpec extends Specification {
    @Subject
    @Autowired
    private OrderRepository orderRepository

    def "It succeeds to update an order's status if the custom repository method returns a Boolean"() {
        given:
        def order = new Order(null, anyString(), "NEW")
        var saved = orderRepository.save(order)


        when:
        def newStatus = orderRepository.updateStatus(saved.id(), "OUT_OF_STOCK")

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
        def wrappedValueShouldBeBoolean = attempt.get() as Try// this should be a Boolean, but is a Try<Boolean>
        Try.isAssignableFrom(wrappedValueShouldBeBoolean.class)
        wrappedValueShouldBeBoolean.success
        wrappedValueShouldBeBoolean.get() == true
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
        def wrappedValueShouldBeBoolean = attempt.get()
        wrappedValueShouldBeBoolean.class == Boolean
        wrappedValueShouldBeBoolean == true
    }


    static String anyString() {
        UUID.randomUUID().toString()
    }
}
