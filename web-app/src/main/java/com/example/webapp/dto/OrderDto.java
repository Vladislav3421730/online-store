package com.example.webapp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;
    private AddressDto address;
    private Long userId;
    private String status;
    private List<OrderItemDto> orderItems;

    public OrderDto(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedAtAsDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }


}
