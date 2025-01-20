package DaoTests;

import com.example.webapp.dao.impl.AddressDaoImpl;
import com.example.webapp.model.Address;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressDaoTest {

    private static AddressDaoImpl addressDao;
    private static Address address;

    @BeforeAll
    static void setup() {
        addressDao = AddressDaoImpl.getInstance();

        address = Address.builder()
                .region("Region 1")
                .town("Town A")
                .exactAddress("123 Main Street")
                .postalCode("10001")
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test save address")
    void testSaveAddress() {
        addressDao.save(address);
        assertNotNull(address.getId());
    }

}
