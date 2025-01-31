package repositoryTests;

import com.example.webapp.model.User;
import com.example.webapp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = TestJpaConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User firstUser;
    private static User secondUser;

    @BeforeAll
    static void setUp() {
        firstUser = User.builder()
                .email("test1@example.com")
                .phoneNumber("1111111111")
                .build();
        secondUser = User.builder()
                .email("test2@example.com")
                .phoneNumber("2222222222")
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test save user")
    void testSave() {
        userRepository.save(firstUser);
        userRepository.save(secondUser);
        assertNotNull(firstUser.getId());
        assertNotNull(secondUser.getId());

    }

    @Test
    @Order(2)
    @DisplayName("Test find user by email")
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("test1@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("test1@example.com", foundUser.get().getEmail());
    }

    @Test
    @Order(3)
    @DisplayName("Test find user by phone number")
    void testFindByPhoneNumber() {
        Optional<User> foundUser = userRepository.findByPhoneNumber("2222222222");
        assertTrue(foundUser.isPresent());
        assertEquals("2222222222", foundUser.get().getPhoneNumber());
    }

    @Test
    @Order(4)
    @DisplayName("Test find user by order id")
    void testFindByOrderById() {
        List<User> users = userRepository.findByOrderById();
        assertFalse(users.isEmpty());
        assertEquals(firstUser.getEmail(), users.get(0).getEmail());
        assertEquals(secondUser.getEmail(), users.get(1).getEmail());
    }

    @Test
    @Order(5)
    @DisplayName("Test find user by  id")
    void testFindUserById() {
        Optional<User> user = userRepository.findById(firstUser.getId());
        assertNotNull(user.get());
    }
}
