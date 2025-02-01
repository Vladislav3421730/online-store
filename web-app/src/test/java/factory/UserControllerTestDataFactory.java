package factory;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserControllerTestDataFactory {

    public static UserDto createFirstUserDto() {
        return UserDto.builder()
                .email("user@gmail.com")
                .username("user")
                .roleSet(Set.of(Role.ROLE_USER.name()))
                .build();
    }

    public static User createFirstUser() {
        return User.builder()
                .roleSet(new HashSet<>())
                .carts(new ArrayList<>())
                .email("test1@example.com")
                .phoneNumber("1111111111")
                .build();
    }

    public static User createSecondUser() {
        return User.builder()
                .email("test2@example.com")
                .phoneNumber("2222222222")
                .build();
    }

    public static RegisterUserDto createRegisterUserDto() {
        return RegisterUserDto.builder()
                .email("user@gmail.com")
                .username("vlad")
                .password("password123")
                .build();
    }

    public static OrderDto createOrderDto() {
        AddressDto addressDto = AddressDto.builder()
                .region("Минская")
                .town("Минск")
                .exactAddress("Леонида Беды 4")
                .build();
        return OrderDto.builder()
                .totalPrice(BigDecimal.valueOf(500.0))
                .address(addressDto)
                .build();
    }
}
