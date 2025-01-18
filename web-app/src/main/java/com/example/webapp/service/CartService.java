package com.example.webapp.service;

import com.example.webapp.dto.CartDto;
import com.example.webapp.model.Cart;

import java.util.List;

public interface CartService {

    boolean incrementAmountOfCartInBasket(List<CartDto> userCarts, int index);
    void decrementAmountOfCartInBasket(List<CartDto> userCarts, int index);
    void deleteCartFromBasket(List<CartDto> cartAfterRemoving, int index);
}
