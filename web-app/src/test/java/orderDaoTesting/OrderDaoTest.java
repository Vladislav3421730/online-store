package orderDaoTesting;

import com.example.webapp.dao.impl.OrderDaoImpl;
import com.example.webapp.dao.impl.UserDaoImpl;
import com.example.webapp.model.Order;
import com.example.webapp.model.User;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderDaoTest {

    private static OrderDaoImpl orderDao;
    private static User userForOrder;
    private static Order order1;
    private static Order order2;


    @BeforeAll
    static void setup() {
        orderDao = OrderDaoImpl.getInstance();
        UserDaoImpl userDao = UserDaoImpl.getInstance();

        userForOrder = OrderTestDataBuilder.createUser();
        userDao.save(userForOrder);

        order1 = OrderTestDataBuilder.createFirstOrder(userForOrder);
        order2 = OrderTestDataBuilder.createSecondOrder(userForOrder);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("Test save order")
    void testSaveOrder() {
        orderDao.save(order1);
        orderDao.save(order2);
        assertNotNull(order1.getId());
        assertNotNull(order2.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("Test find all orders")
    void testFindAll() {
        List<Order> orders = orderDao.findAll();
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(order2.getId(), orders.get(0).getId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("Test find all orders by user's email")
    void testFindAllOrdersByUserEmail() {
        List<Order> orders = orderDao.findAllByUserEmail(userForOrder.getEmail());
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(order2.getId(), orders.get(0).getId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("Test find all orders by wrong user's email")
    void testFindAllOrdersByWrongUserEmail() {
        List<Order> orders = orderDao.findAllByUserEmail("wrongemail@gmail.com");
        assertNotNull(orders);
        assertEquals(0, orders.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("Test find order by id")
    void testFindOrderById() {
        Optional<Order> order = orderDao.findById(order1.getId());
        assertTrue(order.isPresent(),"Order must be not null");
        assertEquals(order.get().getId(),order1.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("Test find order by invalid id")
    void testFindOrderByInvalidId() {
        Optional<Order> order = orderDao.findById(45L);
        assertFalse(order.isPresent(),"Order must be null");
    }


}
