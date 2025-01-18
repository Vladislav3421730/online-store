package orderDaoTesting;

import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderTestDataBuilder {

    public static User createUser() {
        return User.builder()
                .email("user1@gmail.com")
                .build();
    }

    public static Order createFirstOrder(User user) {
        return Order.builder()
                .totalPrice(BigDecimal.valueOf(500))
                .status(Status.ACCEPTED)
                .createdAt(LocalDateTime.now().minusDays(5))
                .user(user)
                .build();
    }

    public static Order createSecondOrder(User user) {
        return Order.builder()
                .totalPrice(BigDecimal.valueOf(600))
                .status(Status.ACCEPTED)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }


}
