package com.example.webapp.mapper;

import com.example.webapp.model.Cart;
import com.example.webapp.model.Order;
import com.example.webapp.model.OrderItem;


public class OderItemCartMapper {

    public static OrderItem map(Cart cart, Order order){
        return OrderItem.builder()
                .product(cart.getProduct())
                .amount(cart.getAmount())
                .order(order)
                .build();
    }
}
