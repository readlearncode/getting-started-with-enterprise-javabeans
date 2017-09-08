package com.readlearncode.util;

import com.readlearncode.generator.ISBNGenerator;
import com.readlearncode.generator.qualifier.ISBN10;
import com.readlearncode.model.Book;
import com.readlearncode.service.BookService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Startup
@Singleton
public class DatabaseInitialiser {

    @EJB
    private BookService bookService;

    @Inject
    @ISBN10
    ISBNGenerator isbnGenerator;

    @PostConstruct
    public void populateDatabase(){

        Book book1 = new Book(isbnGenerator.generateISBN(), "Java EE by Example", "A book about Java EE", "placeholder-cover.jpg", 35.00f, "Alex Theedom");
        bookService.save(book1);

        Book book2 = new Book(isbnGenerator.generateISBN(), "JAX-RS and REST", "A book about JAX-RS", "placeholder-cover.jpg", 15.00f, "Alex Theedom");
        bookService.save(book2);

    }




}