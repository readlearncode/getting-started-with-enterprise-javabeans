package com.readlearncode.service;


import com.readlearncode.model.Book;
import com.readlearncode.model.Order;
import com.readlearncode.model.OrderLine;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {

    @Inject
    private EntityManager entityManager;

    @EJB
    private BookService bookService;

    public List<Order> findAll() {
        TypedQuery<Order> typedQuery = entityManager.createNamedQuery(Order.FIND_ALL, Order.class);
        return typedQuery.getResultList();
    }

    public Order find(Long id) {
        return entityManager.find(Order.class, id);
    }

    public Order save(Order order) {
        order.setCreationDate(new Date());
        for (OrderLine orderLine : order.getOrderLines()) {
            Book book = bookService.find(orderLine.getBook().getId());
            orderLine.setBook(book);
            entityManager.persist(orderLine);
        }
        order.setTotalOrder(calculateOrderTotal(order));
        entityManager.persist(order);
        return order;
    }

    public Order initializeOrder() {
        return save(new Order(new ArrayList<>()));
    }

    private Float calculateOrderTotal(Order order) {
        Float orderTotal = 0f;
        for (OrderLine orderLine : order.getOrderLines()) {
            orderTotal = orderTotal + (orderLine.getQuantity() * orderLine.getBook().getPrice());
        }
        return orderTotal;
    }
}
