package serviceIntegrationTests;


import com.example.webapp.dto.UserDto;
import com.example.webapp.mapper.UserMapper;
import com.example.webapp.mapper.UserMapperImpl;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.AdminService;
import config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static factory.UserControllerTestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = TestConfig.class)
@Slf4j
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapperImpl();

    private static User user;
    private static UserDto userDto;

    @BeforeAll
    static void setUp() {
        user = createFirstUser();
    }

    @Test
    @Order(1)
    @DisplayName("Test bun user")
    void testBunUser() {

        userRepository.save(user);
        userDto = userMapper.toDTO(user);

        adminService.bun(userDto);
        User user = userRepository.findById(userDto.getId()).get();
        assertNotNull(user, "User should be not null");
        assertTrue(user.isBun(), "User should be bun");
    }

    @Test
    @Order(2)
    @DisplayName("Test unbun user")
    void testUnBunUser() {
        userDto.setBun(true);
        adminService.bun(userDto);
        User user = userRepository.findById(userDto.getId()).get();
        assertNotNull(user, "User should be not null");
        assertFalse(user.isBun(), "User should be not bun");
    }

    @Test
    @Order(3)
    @DisplayName("Test add role manager to user")
    void testAddRoleManagerToUser() {
        adminService.madeManager(userDto);
        User user = userRepository.findById(userDto.getId()).get();
        assertNotNull(user, "User should be not null");
        assertTrue(user.getRoleSet().contains(Role.ROLE_MANAGER), "The user must have the manager role");
    }

    @Test
    @Order(4)
    @DisplayName("Test add role manager to user")
    void testRemoveRoleManagerToUser() {
        userDto.getRoleSet().add(String.valueOf(Role.ROLE_MANAGER));
        adminService.madeManager(userDto);
        User user = userRepository.findById(userDto.getId()).get();
        assertNotNull(user, "User should be not null");
        assertFalse(user.getRoleSet().contains(Role.ROLE_MANAGER), "The user must not have the manager role");
    }

}
