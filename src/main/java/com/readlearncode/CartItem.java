package com.readlearncode;

import com.readlearncode.model.Item;

public final class CartItem {

    private Item item;

    private Integer quantity;

    public CartItem(Item item, Integer quantity) {
        setItem(item);
        setQuantity(quantity);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (item != null)
            result += "item: " + item;
        if (quantity != null)
            result += ", quantity: " + quantity;
        return result;
    }
}