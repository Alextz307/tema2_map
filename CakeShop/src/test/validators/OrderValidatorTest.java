package test.validators;

import main.domain.Order;
import main.validators.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {
    private OrderValidator<Integer> validator;

    @BeforeEach
    void setUp() {
        validator = new OrderValidator<>();
    }

    @Test
    void testValidate_WithValidOrder() {
        Order<Integer> validOrder = new Order<>(1, "John Doe", 3);
        validOrder.setStatus(Order.PENDING);
        validOrder.setCreatedAt(validOrder.getCreatedAt());  // Set current time

        assertDoesNotThrow(() -> validator.validate(validOrder), "Validator should not throw exception for valid order");
    }

    @Test
    void testValidate_WithNullCakeId() {
        Order<Integer> orderWithNullCakeId = new Order<>(null, "John Doe", 3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithNullCakeId));
        assertEquals("Cake ID cannot be null.", exception.getMessage());
    }

    @Test
    void testValidate_WithEmptyCustomerName() {
        Order<Integer> orderWithEmptyCustomerName = new Order<>(1, "", 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithEmptyCustomerName));
        assertEquals("Customer name cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidate_WithNullCustomerName() {
        Order<Integer> orderWithNullCustomerName = new Order<>(1, null, 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithNullCustomerName));
        assertEquals("Customer name cannot be empty.", exception.getMessage());
    }

    @Test
    void testValidate_WithNonPositiveQuantity() {
        Order<Integer> orderWithZeroQuantity = new Order<>(1, "John Doe", 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithZeroQuantity));
        assertEquals("Quantity must be positive.", exception.getMessage());
    }

    @Test
    void testValidate_WithNullQuantity() {
        Order<Integer> orderWithNullQuantity = new Order<>(1, "John Doe", null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithNullQuantity));
        assertEquals("Quantity must be positive.", exception.getMessage());
    }

    @Test
    void testValidate_WithInvalidStatus() {
        Order<Integer> orderWithInvalidStatus = new Order<>(1, "John Doe", 3);
        orderWithInvalidStatus.setStatus("Unknown Status");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithInvalidStatus));
        assertEquals("Invalid order status.", exception.getMessage());
    }

    @Test
    void testValidate_WithNullStatus() {
        Order<Integer> orderWithNullStatus = new Order<>(1, "John Doe", 3);
        orderWithNullStatus.setStatus(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithNullStatus));
        assertEquals("Invalid order status.", exception.getMessage());
    }

    @Test
    void testValidate_WithNullCreationDate() {
        Order<Integer> orderWithNullCreatedAt = new Order<>(1, "John Doe", 3);
        orderWithNullCreatedAt.setStatus(Order.PENDING);
        orderWithNullCreatedAt.setCreatedAt(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(orderWithNullCreatedAt));
        assertEquals("Order creation date cannot be null.", exception.getMessage());
    }
}
