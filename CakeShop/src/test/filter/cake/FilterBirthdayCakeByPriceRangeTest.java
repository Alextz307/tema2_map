package test.filter.cake;

import main.domain.BirthdayCake;
import main.filter.cake.FilterBirthdayCakeByPriceRange;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterBirthdayCakeByPriceRangeTest {

    @Test
    void testAccept_WithPriceWithinRange() {
        double minPrice = 20.0;
        double maxPrice = 30.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(1, "Vanilla Dream", "Vanilla", 25.0, 2);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake with price within the range");
    }

    @Test
    void testAccept_WithPriceBelowRange() {
        double minPrice = 20.0;
        double maxPrice = 30.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(2, "Cheap Treat", "Chocolate", 15.0, 1);

        boolean result = filter.accept(cake);

        assertFalse(result, "Filter should not accept the cake with price below the range");
    }

    @Test
    void testAccept_WithPriceAboveRange() {
        double minPrice = 20.0;
        double maxPrice = 30.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(3, "Luxury Chocolate", "Chocolate", 35.0, 3);

        boolean result = filter.accept(cake);

        assertFalse(result, "Filter should not accept the cake with price above the range");
    }

    @Test
    void testAccept_WithPriceEqualToMinRange() {
        double minPrice = 20.0;
        double maxPrice = 30.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(4, "Budget Vanilla", "Vanilla", minPrice, 1);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake with price equal to the minimum range");
    }

    @Test
    void testAccept_WithPriceEqualToMaxRange() {
        double minPrice = 20.0;
        double maxPrice = 30.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(5, "Expensive Treat", "Strawberry", maxPrice, 2);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake with price equal to the maximum range");
    }

    @Test
    void testAccept_WithMinAndMaxPriceEqual() {
        double minPrice = 25.0;
        double maxPrice = 25.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(6, "Exact Match Cake", "Matcha", 25.0, 1);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake when price is exactly equal to both min and max range");
    }

    @Test
    void testAccept_WithNegativePriceRange() {
        double minPrice = -10.0;
        double maxPrice = 30.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(7, "Affordable Surprise", "Vanilla", 5.0, 1);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake within range even if minPrice is negative");
    }

    @Test
    void testAccept_WithInvalidRange_MinGreaterThanMax() {
        double minPrice = 30.0;
        double maxPrice = 20.0;
        FilterBirthdayCakeByPriceRange<Integer> filter = new FilterBirthdayCakeByPriceRange<>(minPrice, maxPrice);
        BirthdayCake<Integer> cake = new BirthdayCake<>(8, "Out of Range", "Blueberry", 25.0, 2);

        boolean result = filter.accept(cake);

        assertFalse(result, "Filter should not accept any cake if minPrice is greater than maxPrice");
    }
}
