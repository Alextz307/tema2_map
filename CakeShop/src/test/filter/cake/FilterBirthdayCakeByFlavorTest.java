package test.filter.cake;

import main.domain.BirthdayCake;
import main.filter.cake.FilterBirthdayCakeByFlavor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterBirthdayCakeByFlavorTest {

    @Test
    void testAccept_WithMatchingFlavor() {
        String desiredFlavor = "Chocolate";
        FilterBirthdayCakeByFlavor<Integer> filter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);
        BirthdayCake<Integer> cake = new BirthdayCake<>(1, "Chocolate Delight", "Chocolate", 25.99, 2);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake with matching flavor");
    }

    @Test
    void testAccept_WithNonMatchingFlavor() {
        String desiredFlavor = "Vanilla";
        FilterBirthdayCakeByFlavor<Integer> filter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);
        BirthdayCake<Integer> cake = new BirthdayCake<>(2, "Chocolate Delight", "Chocolate", 25.99, 2);

        boolean result = filter.accept(cake);

        assertFalse(result, "Filter should not accept the cake with a non-matching flavor");
    }

    @Test
    void testAccept_WithDifferentCaseFlavor() {
        String desiredFlavor = "Strawberry";
        FilterBirthdayCakeByFlavor<Integer> filter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);
        BirthdayCake<Integer> cake = new BirthdayCake<>(3, "Strawberry Surprise", "strawberry", 30.00, 1);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake with matching flavor regardless of case");
    }

    @Test
    void testAccept_WithEmptyDesiredFlavor() {
        String desiredFlavor = "";
        FilterBirthdayCakeByFlavor<Integer> filter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);
        BirthdayCake<Integer> cake = new BirthdayCake<>(6, "Plain Cake", "", 10.00, 1);

        boolean result = filter.accept(cake);

        assertTrue(result, "Filter should accept the cake if both the cake's flavor and desired flavor are empty");
    }

    @Test
    void testAccept_WithSubstringFlavor() {
        String desiredFlavor = "Berry";
        FilterBirthdayCakeByFlavor<Integer> filter = new FilterBirthdayCakeByFlavor<>(desiredFlavor);
        BirthdayCake<Integer> cake = new BirthdayCake<>(7, "Berry Blast", "Blueberry", 18.00, 1);

        boolean result = filter.accept(cake);

        assertFalse(result, "Filter should not accept the cake if desired flavor is a substring of the cake's flavor");
    }
}
