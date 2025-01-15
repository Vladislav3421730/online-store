package com.example.webapp.mapper;

import com.example.webapp.dao.impl.ProductDaoImpl;
import com.example.webapp.model.Cart;
import com.example.webapp.model.Order;
import com.example.webapp.model.OrderItem;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;



public class OderItemCartMapper {

    private static final ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    public static OrderItem map(Cart cart, Order order){
        Product product = cart.getProduct();
        product.setAmount(cart.getProduct().getAmount() - cart.getAmount());
        productDao.update(product);
        return OrderItem.builder()
                .product(cart.getProduct())
                .amount(cart.getAmount())
                .order(order)
                .build();
    }
}
