package com.example.webapp.utils;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.mapper.AddressMapper;
import com.example.webapp.mapper.AddressMapperImpl;
import com.example.webapp.model.Address;
import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.dao.AddressDao;
import com.example.webapp.dao.impl.AddressDaoImpl;
import com.example.webapp.service.AddressService;
import com.example.webapp.service.impl.AddressServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UtilityClass
public class AddressFormationAssistant {

    private final AddressService addressService = AddressServiceImpl.getInstance();

    public AddressDto formAddress(final HttpServletRequest request){
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
            log.info("Address {} already exist in database",address);
        } catch (NumberFormatException e){
            address = addressService.save(address);
            log.info("Adding a new delivery address {}",address);
        }
        return address;
    }

    public void updateAddresses(final HttpServletRequest request){
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        List<AddressDto> addresses = user.getOrders().stream()
                .map(OrderDto::getAddress)
                .distinct()
                .toList();
        log.info("All user delivery addresses {}",addresses);
        request.getSession().setAttribute("addresses", addresses);
    }

}
