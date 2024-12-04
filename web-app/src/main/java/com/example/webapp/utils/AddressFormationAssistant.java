package com.example.webapp.utils;

import com.example.webapp.dao.AddressDao;
import com.example.webapp.model.Address;
import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UtilityClass
public class AddressFormationAssistant {

    private final AddressDao addressDao = AddressDao.getInstance();

    public Address formAddress(HttpServletRequest request){
        long addressId;
        Address address = Address.builder()
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
            addressDao.save(address);
            log.info("Adding a new delivery address {}",address);
        }
        return address;
    }

    public void updateAddresses(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<Address> addresses = user.getOrders().stream()
                .map(Order::getAddress)
                .distinct()
                .toList();
        log.info("All user delivery addresses {}",addresses);
        request.getSession().setAttribute("addresses", addresses);
    }

}
