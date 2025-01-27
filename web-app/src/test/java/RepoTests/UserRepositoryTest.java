package RepoTests;

import com.example.webapp.model.User;
import com.example.webapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = TestJpaConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPhoneNumber("1234567890");
    }

    @Test
    @Transactional
    public void testSave() {
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(savedUser.getEmail(), "test@example.com");
        assertEquals(savedUser.getPhoneNumber(), "1234567890");
    }
}
