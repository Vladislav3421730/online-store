package com.example.webapp.service;

import com.example.webapp.model.Cart;

import java.util.List;

public interface CartService {

    boolean incrementAmountOfCartInBasket(List<Cart> userCarts, int index);
    void decrementAmountOfCartInBasket(List<Cart> userCarts, int index);
    void deleteCartFromBasket(List<Cart> cartAfterRemoving, int index);
}
