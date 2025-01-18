package com.example.webapp.mapper;

import com.example.webapp.dto.*;
import com.example.webapp.model.*;
import com.example.webapp.model.enums.Role;
import com.example.webapp.model.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface UserMapper {

    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    User toNewEntity(RegisterUserDto registerUserDto);

    UserDto toDTO(User user);

    User toEntity(UserDto userDto);

    default List<CartDto> mapCartsToCartDtos(List<Cart> carts){
        if(carts==null){
            return null;
        }
        return carts.stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }
    default List<OrderDto> mapOrdersToOrderDtos(List<Order> orders){
        if(orders==null){
            return null;
        }
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    default List<Cart> mapCartsDtosToCart(List<CartDto> carts){
        if(carts==null){
            return null;
        }
        return carts.stream()
                .map(cartMapper::toEntity)
                .collect(Collectors.toList());
    }
    default List<Order> mapOrdersDtosToOrders(List<OrderDto> orders){
        if(orders==null){
            return null;
        }
        return orders.stream()
                .map(orderMapper::toEntity)
                .collect(Collectors.toList());
    }

    default Set<String> mapImageListToIds(Set<Role> roleSet) {
        if (roleSet == null) {
            return null;
        }
        return roleSet.stream()
                .map(Role::name)
                .collect(Collectors.toSet());
    }

}

