package DaoTests.userDaoTesting;

import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;

import java.util.Set;

public class UserTestDataBuilder {

    public static User createFirstUser() {
        return User.builder()
                .email("user1@gmail.com")
                .username("user1")
                .phoneNumber("+375445716964")
                .build();
    }

    public static User createSecondUser() {
        return User.builder()
                .email("user2@gmail.com")
                .username("user2")
                .phoneNumber("+375445716965")
                .build();
    }

    public static User createThirdUser() {
        return User.builder()
                .email("user3@gmail.com")
                .username("user3")
                .phoneNumber("+375445716966")
                .roleSet(Set.of(Role.ROLE_ADMIN))
                .build();
    }

    public static User createUserWithNotUniqueEmail() {
        return User.builder()
                .email("user1@gmail.com")
                .build();
    }

    public static User createUserWithNotUniquePhoneNumber() {
        return User.builder()
                .phoneNumber("+375445716964")
                .build();
    }


}
