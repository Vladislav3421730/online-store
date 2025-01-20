package DaoTests;

import com.example.webapp.dao.impl.CartDaoImpl;
import com.example.webapp.model.Cart;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartDaoTest {

    private static CartDaoImpl cartDao;

    private static Cart cart;

    @BeforeAll
    static void setup() {
        cartDao = CartDaoImpl.getInstance();
        cart = Cart.builder()
                .amount(5)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test save cart")
    void testSaveCart() {
        cartDao.save(cart);
        assertNotNull(cart.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Test increment amount in Cart")
    void testIncrementAmountInCart() {
        cartDao.incrementAmount(cart.getId());
        Optional<Cart> incrementedCart = cartDao.findById(cart.getId());
        assertTrue(incrementedCart.isPresent());
        assertEquals(6, incrementedCart.get().getAmount());
    }

    @Test
    @Order(3)
    @DisplayName("Test decrement amount in Cart")
    void testDecrementAmountInCart() {
        cartDao.decrementAmount(cart.getId());
        Optional<Cart> incrementedCart = cartDao.findById(cart.getId());
        assertTrue(incrementedCart.isPresent());
        assertEquals(5, incrementedCart.get().getAmount());
    }

    @Test
    @Order(4)
    @DisplayName("Test delete Cart")
    void testDeleteCart() {
        cartDao.delete(cart.getId());
        Optional<Cart> deletedCart = cartDao.findById(cart.getId());
        assertFalse(deletedCart.isPresent(),"Cart should be deleted");
    }

}
