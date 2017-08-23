package com.readlearncode.service;

import com.readlearncode.model.Book;
import com.readlearncode.model.Order;
import com.readlearncode.model.OrderLine;

import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Stateful
public class ShoppingCart {

    @Inject
    private BookService bookService;

    @Inject
    private OrderService orderService;

    private Order order;

    public void initializeCart() {
        order = orderService.initializeOrder();
    }

    public Order find(Long id) {
        return orderService.find(id);
    }

    public void save(Order order) {
        orderService.save(order);
    }

    public void addBook(Book book, Integer quantity) {

        order = orderService.find(order.getId()); //reattach order

        boolean exists = false;
        for (OrderLine orderLine : order.getOrderLines()) {
            if (orderLine.getBook().getId().equals(book.getId())) {
                orderLine.addQuantity(quantity);
                exists = true;
                break;
            }
        }

        if(!exists){
            book = bookService.find(book.getId()); // reattach book
            order.getOrderLines().add(new OrderLine(book, quantity));
        }

        save(this.order);
    }


    public Order getOrder() {
        return order;
    }
}