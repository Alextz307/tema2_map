package test.filter.order;

import main.domain.Order;
import main.filter.order.FilterOrderByQuantity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterOrderByQuantityTest {

    @Test
    void testAccept_WithQuantityWithinRange() {
        int minQuantity = 1;
        int maxQuantity = 10;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(1, "John Doe", 5);

        boolean result = filter.accept(order);

        assertTrue(result, "Filter should accept the order with quantity within the range");
    }

    @Test
    void testAccept_WithQuantityBelowRange() {
        int minQuantity = 5;
        int maxQuantity = 10;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(2, "Jane Doe", 3);

        boolean result = filter.accept(order);

        assertFalse(result, "Filter should not accept the order with quantity below the range");
    }

    @Test
    void testAccept_WithQuantityAboveRange() {
        int minQuantity = 1;
        int maxQuantity = 10;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(3, "Alice Smith", 15);

        boolean result = filter.accept(order);

        assertFalse(result, "Filter should not accept the order with quantity above the range");
    }

    @Test
    void testAccept_WithQuantityEqualToMinRange() {
        int minQuantity = 3;
        int maxQuantity = 10;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(4, "Bob Brown", minQuantity);

        boolean result = filter.accept(order);

        assertTrue(result, "Filter should accept the order with quantity equal to the minimum range");
    }

    @Test
    void testAccept_WithQuantityEqualToMaxRange() {
        int minQuantity = 3;
        int maxQuantity = 10;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(5, "Charlie Green", maxQuantity);

        boolean result = filter.accept(order);

        assertTrue(result, "Filter should accept the order with quantity equal to the maximum range");
    }

    @Test
    void testAccept_WithMinAndMaxQuantityEqual() {
        int minQuantity = 5;
        int maxQuantity = 5;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(6, "Exact Quantity", 5);

        boolean result = filter.accept(order);

        assertTrue(result, "Filter should accept the order when quantity is exactly equal to both min and max range");
    }

    @Test
    void testAccept_WithInvalidRange_MinGreaterThanMax() {
        int minQuantity = 10;
        int maxQuantity = 5;
        FilterOrderByQuantity<Integer> filter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        Order<Integer> order = new Order<>(7, "Invalid Range Order", 8);

        boolean result = filter.accept(order);

        assertFalse(result, "Filter should not accept any order if minQuantity is greater than maxQuantity");
    }
}
