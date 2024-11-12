package test.domain;

import main.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order<Integer> order;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setUp() {
        order = new Order<>(1001, "John Doe", 3);
    }

    @Test
    public void testConstructor() {
        assertEquals(1001, order.getCakeId());
        assertEquals("John Doe", order.getCustomerName());
        assertEquals(3, order.getQuantity());
        assertEquals(Order.PENDING, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    public void testGetAndSetOrderId() {
        assertNull(order.getId());
        order.setId(5001);
        assertEquals(5001, order.getId());
    }

    @Test
    public void testGetStatus() {
        assertEquals(Order.PENDING, order.getStatus());
    }

    @Test
    public void testSetStatus() {
        order.setStatus(Order.FINISHED);
        assertEquals(Order.FINISHED, order.getStatus());

        order.setStatus(Order.CANCELLED);
        assertEquals(Order.CANCELLED, order.getStatus());

        order.setStatus("CustomStatus");
        assertEquals("CustomStatus", order.getStatus());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(3, order.getQuantity());
    }

    @Test
    public void testSetCreatedAt() {
        LocalDateTime newDate = LocalDateTime.of(2023, 1, 1, 12, 0);
        order.setCreatedAt(newDate);
        assertEquals(newDate, order.getCreatedAt());
    }

    @Test
    public void testGetCakeId() {
        assertEquals(1001, order.getCakeId());
    }

    @Test
    public void testGetCustomerName() {
        assertEquals("John Doe", order.getCustomerName());
    }

    @Test
    public void testToString() {
        order.setId(1234);
        String expectedString = "1234,1001,John Doe,3,Pending," + order.getCreatedAt().format(formatter);
        assertEquals(expectedString, order.toString());
    }

    @Test
    public void testNullCustomerName() {
        Order<Integer> nullCustomerOrder = new Order<>(2002, null, 1);
        assertNull(nullCustomerOrder.getCustomerName());
        assertEquals(Order.PENDING, nullCustomerOrder.getStatus());
        assertEquals(1, nullCustomerOrder.getQuantity());
    }

    @Test
    public void testNegativeQuantity() {
        Order<Integer> negativeQuantityOrder = new Order<>(3003, "Jane Smith", -5);
        assertEquals(-5, negativeQuantityOrder.getQuantity());
    }

    @Test
    public void testZeroQuantity() {
        Order<Integer> zeroQuantityOrder = new Order<>(4004, "Alex Johnson", 0);
        assertEquals(0, zeroQuantityOrder.getQuantity());
    }
}
