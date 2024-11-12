package test.repository.file;

import main.domain.Order;
import main.repository.file.text.OrderTextFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTextFileRepositoryTest {
    private static final String FILENAME = "test_orders.txt";
    private OrderTextFileRepository<Integer> repository;

    @BeforeEach
    public void setUp() {
        repository = new OrderTextFileRepository<>(FILENAME);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd() {
        Order<Integer> order = new Order<>(1, "John Doe", 5);
        order.setId(1);
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());

        repository.add(order);
        List<Order<Integer>> orders = (List<Order<Integer>>) repository.findAll();

        assertEquals(1, orders.size(), "Should have one order after adding");
        assertEquals(order.getCustomerName(), orders.getFirst().getCustomerName());
    }
}
