package com.example.webapp.dto;

import com.example.webapp.model.Address;
import com.example.webapp.model.OrderItem;
import com.example.webapp.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;
    private Address address;

    private String status;

    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderDto(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedAtAsDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }


}
