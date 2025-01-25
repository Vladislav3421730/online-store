package com.example.webapp.utils;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class UserHelperUtils {

    public List<AddressDto> updateAddresses(UserDto user) {
        return user.getOrders().stream()
                .map(OrderDto::getAddress)
                .distinct()
                .toList();
    }

    public BigDecimal getCoast(UserDto user) {
        return user.getCarts().stream()
                .map(x -> x.getProduct().getCoast().multiply(BigDecimal.valueOf(x.getAmount())))
                .reduce(BigDecimal::add).get();
    }

}
