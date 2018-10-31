package com.readlearncode.service;

import com.readlearncode.model.Book;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Stateless
@Path("/books")
public class BookRestService {

    @EJB
    private BookService bookService;

    @GET
    @Produces("text/xml")
    public Book getBook(@QueryParam("id") Long id) {
        return bookService.find(id);
    }

}
