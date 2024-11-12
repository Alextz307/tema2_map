package test.validators;

import main.domain.BirthdayCake;
import main.validators.CakeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CakeValidatorTest {
    private CakeValidator<Integer> validator;

    @BeforeEach
    void setUp() {
        validator = new CakeValidator<>();
    }

    @Test
    void testValidate_WithValidCake() {
        BirthdayCake<Integer> validCake = new BirthdayCake<>(1, "Chocolate Delight", "Chocolate", 25.99, 2);

        assertDoesNotThrow(() -> validator.validate(validCake), "Validator should not throw exception for valid cake");
    }

    @Test
    void testValidate_WithEmptyName() {
        BirthdayCake<Integer> cakeWithEmptyName = new BirthdayCake<>(1, "", "Chocolate", 25.99, 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithEmptyName));
        assertEquals("Cake name cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidate_WithNullName() {
        BirthdayCake<Integer> cakeWithNullName = new BirthdayCake<>(1, null, "Chocolate", 25.99, 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithNullName));
        assertEquals("Cake name cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidate_WithEmptyFlavor() {
        BirthdayCake<Integer> cakeWithEmptyFlavor = new BirthdayCake<>(1, "Vanilla Dream", "", 20.49, 3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithEmptyFlavor));
        assertEquals("Cake flavor cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidate_WithNullFlavor() {
        BirthdayCake<Integer> cakeWithNullFlavor = new BirthdayCake<>(1, "Vanilla Dream", null, 20.49, 3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithNullFlavor));
        assertEquals("Cake flavor cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidate_WithNonPositivePrice() {
        BirthdayCake<Integer> cakeWithZeroPrice = new BirthdayCake<>(1, "Budget Cake", "Chocolate", 0.0, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithZeroPrice));
        assertEquals("Cake price must be positive.", exception.getMessage());
    }

    @Test
    void testValidate_WithNegativePrice() {
        BirthdayCake<Integer> cakeWithNegativePrice = new BirthdayCake<>(1, "Luxury Cake", "Chocolate", -15.0, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithNegativePrice));
        assertEquals("Cake price must be positive.", exception.getMessage());
    }

    @Test
    void testValidate_WithNonPositiveLayers() {
        BirthdayCake<Integer> cakeWithZeroLayers = new BirthdayCake<>(1, "Layerless Cake", "Vanilla", 10.0, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithZeroLayers));
        assertEquals("Cake must have at least 1 layer.", exception.getMessage());
    }

    @Test
    void testValidate_WithNegativeLayers() {
        BirthdayCake<Integer> cakeWithNegativeLayers = new BirthdayCake<>(1, "Negative Layers Cake", "Strawberry", 10.0, -1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(cakeWithNegativeLayers));
        assertEquals("Cake must have at least 1 layer.", exception.getMessage());
    }
}
