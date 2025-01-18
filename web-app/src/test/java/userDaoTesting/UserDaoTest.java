package userDaoTesting;

import com.example.webapp.dao.impl.UserDaoImpl;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import org.junit.jupiter.api.*;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTest {

    private static UserDaoImpl userDao;

    private static User firstUser;
    private static User secondUser;
    private static User thirdUser;

    private static User userWithNotUniqueEmail;
    private static User userWithNotUniquePhoneNumber;

    @BeforeAll
    static void setup() {
        userDao = UserDaoImpl.getInstance();
        firstUser = UserTestDataBuilder.createFirstUser();
        secondUser = UserTestDataBuilder.createSecondUser();
        thirdUser = UserTestDataBuilder.createThirdUser();
        userWithNotUniqueEmail = UserTestDataBuilder
                .createUserWithNotUniqueEmail();
        userWithNotUniquePhoneNumber = UserTestDataBuilder
                .createUserWithNotUniquePhoneNumber();
    }

    @Test
    @Order(1)
    @DisplayName("Test save user")
    void testSaveUser() {
        userDao.save(firstUser);
        userDao.save(secondUser);
        userDao.save(thirdUser);

        assertNotNull(firstUser.getId());
        assertNotNull(secondUser.getId());
        assertNotNull(thirdUser.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Test save user with not unique email")
    void testSaveUserWithNotUniqueEmail() {
        assertThrows(PersistenceException.class,
                () -> userDao.save(userWithNotUniqueEmail));
    }

    @Test
    @Order(3)
    @DisplayName("Test save user with not unique phone number")
    void testSaveUserWithNotUniquePhoneNumber() {
        assertThrows(PersistenceException.class,
                () -> userDao.save(userWithNotUniquePhoneNumber));
    }

    @Test
    @Order(4)
    @DisplayName("Test find user by id")
    void testFindUserById() {
        Optional<User> user = userDao.findById(firstUser.getId());
        assertTrue(user.isPresent(), "User must be not null");
    }

    @Test
    @Order(5)
    @DisplayName("Test find user by invalid id")
    void testFindUserByInvalidId() {
        Optional<User> user = userDao.findById(45L);
        assertFalse(user.isPresent(), "User must be null");
    }

    @Test
    @Order(6)
    @DisplayName("Test find all users")
    void testFindAllUsers() {
        List<User> users = userDao.findAll();
        assertEquals(3, users.size());
    }

    @Test
    @Order(7)
    @DisplayName("Test find user by email")
    void testFindUserByEmail() {
        Optional<User> user = userDao.findByEmail(firstUser.getEmail());
        assertTrue(user.isPresent(), "User must be not null");
        assertEquals(firstUser.getEmail(), user.get().getEmail());
    }

    @Test
    @Order(8)
    @DisplayName("Test find user by phone number")
    void testFindUserByPhoneNumber() {
        Optional<User> user = userDao.findByPhoneNumber(firstUser.getPhoneNumber());
        assertTrue(user.isPresent(), "User must be not null");
        assertEquals(firstUser.getPhoneNumber(), user.get().getPhoneNumber());
    }

    @Test
    @Order(9)
    @DisplayName("Test add role manager to user")
    void testAddRoleManagerToUser() {
        userDao.addRoleManagerToUser(firstUser.getId());
        Optional<User> user = userDao.findById(firstUser.getId());
        assertTrue(user.isPresent(), "User must be not null");
        assertTrue(user.get().getRoleSet().contains(Role.ROLE_MANAGER), "User must have ROLE_MANAGER");
    }

    @Test
    @Order(10)
    @DisplayName("Test remove role manager from user")
    void testRemoveRoleManagerFromUser() {
        userDao.addRoleManagerToUser(firstUser.getId());
        userDao.removeRoleManagerFromUser(firstUser.getId());
        Optional<User> user = userDao.findById(firstUser.getId());
        assertTrue(user.isPresent(), "User must be not null");
        assertFalse(user.get().getRoleSet().contains(Role.ROLE_MANAGER), "User must not have ROLE_MANAGER");
    }

    @Test
    @Order(11)
    @DisplayName("Test bun/unbun user")
    void testBunUser() {
        Optional<User> userBefore = userDao.findById(firstUser.getId());
        assertTrue(userBefore.isPresent(), "User must be not null");
        assertFalse(userBefore.get().isBun(), "User must not be bun");

        userDao.bunUser(firstUser.getId());
        Optional<User> userAfterBun = userDao.findById(firstUser.getId());
        assertTrue(userAfterBun.isPresent(), "User must be not null");
        assertTrue(userAfterBun.get().isBun(), "User must be bun");

        userDao.bunUser(firstUser.getId());
        Optional<User> userAfterUnbun = userDao.findById(firstUser.getId());
        assertTrue(userAfterUnbun.isPresent(), "User must be not null");
        assertFalse(userAfterUnbun.get().isBun(), "User must not be bun");
    }



}
