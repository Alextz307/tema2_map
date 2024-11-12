package main.validators;

import main.domain.Order;

public class OrderValidator<ID> {
    public void validate(Order<ID> order) {
        validateOrder(order);
    }

    private void validateOrder(Order<ID> order) {
        if (order.getCakeId() == null) {
            throw new IllegalArgumentException("Cake ID cannot be null.");
        }

        if (order.getCustomerName() == null || order.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        if (order.getStatus() == null ||
                (!order.getStatus().equals(Order.PENDING) &&
                        !order.getStatus().equals(Order.FINISHED) &&
                        !order.getStatus().equals(Order.CANCELLED))) {
            throw new IllegalArgumentException("Invalid order status.");
        }

        if (order.getCreatedAt() == null) {
            throw new IllegalArgumentException("Order creation date cannot be null.");
        }
    }
}
