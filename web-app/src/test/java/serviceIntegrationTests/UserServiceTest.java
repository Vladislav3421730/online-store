package serviceIntegrationTests;


import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.exception.UserNotFoundException;
import com.example.webapp.model.User;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import config.TestConfig;

import static org.junit.jupiter.api.Assertions.*;
import static factory.UserControllerTestDataFactory.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = TestConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private static RegisterUserDto registerUserDto;
    private static User user;

    @BeforeAll
    static void setUp() {
        user = createFirstUser();
        registerUserDto = createRegisterUserDto();
    }

    @Test
    @Order(1)
    @DisplayName("Test save user")
    void testSaveUser() {
        userService.save(registerUserDto);
        User savedUser = userRepository.findByEmail(registerUserDto.getEmail()).orElse(null);
        assertNotNull(savedUser, "User should be saved in the database");
        assertEquals(registerUserDto.getEmail(), savedUser.getEmail());
        assertNotNull(savedUser.getPassword(), "Password should be encoded and not null");
    }

    @Test
    @Order(2)
    @DisplayName("Test find user by email")
    void testFindUserByEmail() {
        userRepository.save(user);
        UserDto userDto = userService.findByEmail(user.getEmail()).orElse(null);
        assertNotNull(userDto, "User should be found by email");
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    @Order(3)
    @DisplayName("Test find user by id")
    void testFindUserById() {
        UserDto userDto = userService.findById(user.getId());
        assertNotNull(userDto, "User should be found by email");
        assertEquals(user.getId(), userDto.getId());
    }

    @Test
    @Order(4)
    @DisplayName("Test find user by wrong id")
    void testFindUserByWrongId() {
        assertThrows(UserNotFoundException.class,
                () -> userService.findById(Long.MAX_VALUE));
    }

    @Test
    @Order(5)
    @DisplayName("Test make order")
    @Transactional
    void testMakeOrder() {
        OrderDto orderDto = createOrderDto();

        UserDto userDto = userService.findByEmail(user.getEmail()).orElse(null);
        assertNotNull(userDto, "User should be found before making order");

        UserDto updatedUser = userService.makeOrder(userDto, orderDto);
        assertNotNull(updatedUser, "User should be updated after making an order");
        assertTrue(updatedUser.getCarts().isEmpty(), "Carts should be cleared after making the order");

        com.example.webapp.model.Order savedOrder = orderRepository.findById(updatedUser.getOrders().get(0).getId()).orElse(null);
        assertNotNull(savedOrder, "The order should be saved in the database");

        assertEquals(user.getEmail(), savedOrder.getUser().getEmail(), "Order should belong to the correct user");

        assertNotNull(savedOrder.getAddress().getId(), "Address should contain id");
    }

}
