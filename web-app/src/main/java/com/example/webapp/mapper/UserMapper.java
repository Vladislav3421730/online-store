package com.example.webapp.mapper;

import com.example.webapp.dto.CartDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.model.Cart;
import com.example.webapp.model.Image;
import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface UserMapper {

    CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    User toEntity(RegisterUserDto registerUserDto);

    @Mapping(source = "carts", target = "carts")
    @Mapping(source = "orders", target = "orders")
    UserDto toDTO(User user);


    List<CartDto> mapCartsToCartDtos(List<Cart> carts);
    List<OrderDto> mapOrdersToOrderDtos(List<Order> orders);

    default Set<String> mapImageListToIds(Set<Role> roleSet) {
        if (roleSet == null) {
            return null;
        }
        return roleSet.stream()
                .map(Role::name)
                .collect(Collectors.toSet());
    }

    default List<Long> mapImageListToIds(List<Image> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }
}

