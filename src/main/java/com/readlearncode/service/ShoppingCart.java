package com.readlearncode.service;

import com.readlearncode.CartItem;
import com.readlearncode.model.Item;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.ArrayList;

@Stateful
@LocalBean
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -1L;
    private ArrayList<CartItem> cartItems;

    @PostConstruct
    private void initialiseCart() {
        cartItems = new ArrayList<>();
    }

    public ArrayList getCartItems() {
        return cartItems;
    }

    public void addItem(Item item, Integer quantity) {
        new CartItem(item, quantity);
    }

    public void updateItem(Item item, Integer quantity) {
        removeItem(item);
        addItem(item, quantity);
    }

    public void removeItem(Item item) {
        cartItems.remove(item);
    }


    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (cartItems != null)
            result += "cartItems: " + cartItems;
        return result;
    }
}