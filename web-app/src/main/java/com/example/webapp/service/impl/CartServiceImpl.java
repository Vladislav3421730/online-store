package com.example.webapp.service.impl;


import com.example.webapp.exception.WrongIndexException;
import com.example.webapp.model.Cart;
import com.example.webapp.service.CartService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class CartServiceImpl implements CartService {

    private final static CartServiceImpl INSTANCE = new CartServiceImpl();

    public static CartServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean incrementAmountOfCartInBasket(List<Cart> userCarts, int index) {
        if (index < 0 || index >= userCarts.size()) {
            log.error("Index out of bounds: {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        Cart cart = userCarts.get(index);
        if (cart.getAmount() + 1 > cart.getProduct().getAmount()) {
            log.error("The quantity of goods in the basket is equal to the quantity in the warehouse," +
                    " it is impossible to increase the quantity of goods {}", cart.getProduct().getAmount());
            return false;
        }
        cart.setAmount(cart.getAmount() + 1);
        userCarts.set(index, cart);
        log.info("The quantity of the product {} has been increased by 1 " +
                "{}", cart.getProduct().getTitle(), cart.getAmount());
        return true;
    }

    @Override
    public void decrementAmountOfCartInBasket(List<Cart> userCarts, int index) {
        if (index < 0 || index >= userCarts.size()) {
            log.error("Index out of bounds: {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        Cart cart = userCarts.get(index);
        if (cart.getAmount() == 1) {
            userCarts.remove(index);
            log.info("The product {} has been removed from the user's {} cart ",
                    cart.getUser().getEmail(), cart.getProduct().getTitle());
        } else {
            cart.setAmount(cart.getAmount() - 1);
            userCarts.set(index, cart);
            log.info("The quantity of good {} was reduced by one and now {}",
                    cart.getProduct().getTitle(), cart.getUser().getEmail());
        }
    }

    @Override
    public void deleteCartFromBasket(List<Cart> cartAfterRemoving, int index) {
        if (index < 0 || index >= cartAfterRemoving.size()) {
            log.error("Index out of bounds {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        cartAfterRemoving.remove(index);
        log.info("The product has been removed from the user's  cart");
    }
}
