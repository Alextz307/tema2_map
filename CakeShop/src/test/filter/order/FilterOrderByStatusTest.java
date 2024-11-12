package test.filter.order;

import main.domain.Order;
import main.filter.order.FilterOrderByStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterOrderByStatusTest {

    @Test
    void testAccept_WithMatchingStatus() {
        String desiredStatus = Order.PENDING;
        FilterOrderByStatus<Integer> filter = new FilterOrderByStatus<>(desiredStatus);
        Order<Integer> order = new Order<>(1, "John Doe", 5);
        order.setStatus(Order.PENDING);

        boolean result = filter.accept(order);

        assertTrue(result, "Filter should accept the order with matching status");
    }

    @Test
    void testAccept_WithNonMatchingStatus() {
        String desiredStatus = Order.FINISHED;
        FilterOrderByStatus<Integer> filter = new FilterOrderByStatus<>(desiredStatus);
        Order<Integer> order = new Order<>(2, "Jane Doe", 3);
        order.setStatus(Order.PENDING);

        boolean result = filter.accept(order);

        assertFalse(result, "Filter should not accept the order with non-matching status");
    }

    @Test
    void testAccept_WithDifferentCaseStatus() {
        String desiredStatus = Order.CANCELLED;
        FilterOrderByStatus<Integer> filter = new FilterOrderByStatus<>(desiredStatus.toLowerCase()); // Using lower case for test
        Order<Integer> order = new Order<>(3, "Alice Smith", 2);
        order.setStatus(Order.CANCELLED);

        boolean result = filter.accept(order);

        assertTrue(result, "Filter should accept the order with matching status regardless of case");
    }

    @Test
    void testAccept_WithNullDesiredStatus() {
        FilterOrderByStatus<Integer> filter = new FilterOrderByStatus<>(null);
        Order<Integer> order = new Order<>(4, "Bob Brown", 4);
        order.setStatus(Order.PENDING);

        boolean result = filter.accept(order);

        assertFalse(result, "Filter should not accept any order if desired status is null");
    }

    @Test
    void testAccept_WithEmptyDesiredStatus() {
        String desiredStatus = "";
        FilterOrderByStatus<Integer> filter = new FilterOrderByStatus<>(desiredStatus);
        Order<Integer> order = new Order<>(6, "Eve White", 2);
        order.setStatus(Order.PENDING);

        boolean result = filter.accept(order);

        assertFalse(result, "Filter should not accept the order if desired status is empty");
    }
}
