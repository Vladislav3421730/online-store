package com.example.webapp.utils;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddressFormationAssistant {

    AddressService addressService;

    public AddressDto formAddress(final HttpServletRequest request) {
        long addressId;
        AddressDto address = AddressDto.builder()
                .region(request.getParameter("region"))
                .town(request.getParameter("town"))
                .exactAddress(request.getParameter("exactAddress"))
                .postalCode(request.getParameter("postalCode"))
                .build();
        try {
            addressId = Long.parseLong(request.getParameter("addressId"));
            address.setId(addressId);
            log.info("Address {} already exist in database", address);
        } catch (NumberFormatException e) {
            address = addressService.save(address);
            log.info("Adding a new delivery address {}", address);
        }
        return address;
    }

    public List<AddressDto> updateAddresses(UserDto user) {
        return user.getOrders().stream()
                .map(OrderDto::getAddress)
                .distinct()
                .toList();
    }

}
