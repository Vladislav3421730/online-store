package factory;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.model.enums.Role;

import java.util.Set;

public class UserControllerTestDataFactory {

    public static UserDto createFirstUserDto() {
        return UserDto.builder()
                .email("user@gmail.com")
                .username("user")
                .isBun(false)
                .roleSet(Set.of(Role.ROLE_USER.name()))
                .build();
    }

    public static AddressDto createAddressDto(){
        return AddressDto.builder()
                .town("Минск")
                .region("Минская")
                .exactAddress("адресс")
                .build();
    }

}
