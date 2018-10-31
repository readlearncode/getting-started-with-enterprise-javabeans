package com.readlearncode.service;

import com.readlearncode.model.Book;

import javax.jws.WebService;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@WebService
interface BookSoapService {
    Book find(Long id);
}
