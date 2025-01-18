package com.example.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_of_goods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 1,message = "cart's must must be more or equal than 1")
    private int amount;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull(message = "The product in the cart cannot be null")
    private Product product;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User in cart cannot be null")
    private User user;

    public Cart(Product product) {
        this.amount=1;
        this.product = product;
    }
}
