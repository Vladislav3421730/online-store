package com.example.webapp.mapper;

import com.example.webapp.model.Cart;
import com.example.webapp.model.Order;
import com.example.webapp.model.OrderItem;
import com.example.webapp.model.Product;
import com.example.webapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OderItemCartMapper {

    @Autowired
    private ProductRepository productRepository;

    public OrderItem map(Cart cart, Order order){
        Product product = cart.getProduct();
        product.setAmount(cart.getProduct().getAmount() - cart.getAmount());
        productRepository.save(product);
        return OrderItem.builder()
                .product(cart.getProduct())
                .amount(cart.getAmount())
                .order(order)
                .build();
    }
}
