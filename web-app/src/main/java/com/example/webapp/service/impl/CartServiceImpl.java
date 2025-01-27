package com.example.webapp.service.impl;

import com.example.webapp.dto.CartDto;
import com.example.webapp.exception.WrongIndexException;
import com.example.webapp.mapper.CartMapper;
import com.example.webapp.mapper.CartMapperImpl;
import com.example.webapp.model.Cart;
import com.example.webapp.repository.CartRepository;
import com.example.webapp.service.CartService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartMapper cartMapper;

    @Override
    @Transactional
    public boolean incrementAmountOfCartInBasket(List<CartDto> userCarts, int index) {
        if (index < 0 || index >= userCarts.size()) {
            log.error("Index out of bounds: {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        CartDto cart = userCarts.get(index);
        if (cart.getAmount() + 1 > cart.getProduct().getAmount()) {
            log.error("The quantity of goods in the basket is equal to the quantity in the warehouse, " +
                            "it is impossible to increase the quantity of goods {}. Available quantity in warehouse: {}",
                    cart.getProduct().getTitle(), cart.getProduct().getAmount());
            return false;
        }
        cart.setAmount(cart.getAmount() + 1);
        userCarts.set(index, cart);
        Cart updatedCart = cartMapper.toEntity(cart);
        cartRepository.save(updatedCart);
        log.info("The quantity of product '{}' has been increased by 1. New quantity: {}",
                cart.getProduct().getTitle(), cart.getAmount());
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
            cartRepository.deleteById(cart.getId());
            log.info("The product '{}' has been removed from the user's cart", cart.getProduct().getTitle());
        } else {
            cart.setAmount(cart.getAmount() - 1);
            userCarts.set(index, cart);
            Cart updatedCart = cartMapper.toEntity(cart);
            cartRepository.save(updatedCart);
            log.info("The quantity of product '{}' has been reduced by 1. New quantity: {}",
                    cart.getProduct().getTitle(), cart.getAmount());
        }
    }

    @Override
    @Transactional
    public void deleteCartFromBasket(List<CartDto> cartAfterRemoving, int index) {
        if (index < 0 || index >= cartAfterRemoving.size()) {
            log.error("Index out of bounds: {}", index);
            throw new WrongIndexException("Index out of bounds: " + index);
        }
        CartDto cart = cartAfterRemoving.get(index);
        cartRepository.deleteById(cart.getId());
        cartAfterRemoving.remove(index);
        log.info("The product '{}' has been removed from the user's cart", cart.getProduct().getTitle());
    }
}
