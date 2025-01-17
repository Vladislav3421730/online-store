package com.example.webapp.service.impl;

import com.example.webapp.dao.impl.CartDaoImpl;
import com.example.webapp.dto.CartDto;
import com.example.webapp.exception.WrongIndexException;
import com.example.webapp.service.CartService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartServiceImpl implements CartService {

    private final static CartServiceImpl INSTANCE = new CartServiceImpl();

    public static CartServiceImpl getInstance() {
        return INSTANCE;
    }

    CartDaoImpl cartDao = CartDaoImpl.getInstance();

    @Override
    @Transactional
    public boolean incrementAmountOfCartInBasket(List<CartDto> userCarts, int index) {
        if (index < 0 || index >= userCarts.size()) {
            log.error("Index out of bounds: {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        CartDto cart = userCarts.get(index);
        if (cart.getAmount() + 1 > cart.getProduct().getAmount()) {
            log.error("The quantity of goods in the basket is equal to the quantity in the warehouse," +
                    " it is impossible to increase the quantity of goods {}", cart.getProduct().getAmount());
            return false;
        }
        cart.setAmount(cart.getAmount() + 1);
        userCarts.set(index, cart);
        cartDao.incrementAmount(cart.getId());
        log.info("The quantity of the product {} has been increased by 1 " +
                "{}", cart.getProduct().getTitle(), cart.getAmount());
        return true;
    }

    @Override
    @Transactional
    public void decrementAmountOfCartInBasket(List<CartDto> userCarts, int index) {
        if (index < 0 || index >= userCarts.size()) {
            log.error("Index out of bounds: {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        CartDto cart = userCarts.get(index);
        if (cart.getAmount() == 1) {
            userCarts.remove(index);
            cartDao.delete(cart.getId());
            log.info("The product {} has been removed from the user's cart ",
                    cart.getProduct().getTitle());
        } else {
            cart.setAmount(cart.getAmount() - 1);
            userCarts.set(index, cart);
            cartDao.decrementAmount(cart.getId());
            log.info("The quantity of good {} was reduced by one",
                    cart.getProduct().getTitle());
        }
    }

    @Override
    @Transactional
    public void deleteCartFromBasket(List<CartDto> cartAfterRemoving, int index) {
        if (index < 0 || index >= cartAfterRemoving.size()) {
            log.error("Index out of bounds {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        cartDao.delete(cartAfterRemoving.get(index).getId());
        cartAfterRemoving.remove(index);
        log.info("The product has been removed from the user's  cart");
    }
}
