package main.service;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.filter.order.FilterOrderByQuantity;
import main.filter.order.FilterOrderByStatus;
import main.repository.IRepository;
import main.repository.memory.InMemoryFilteredRepository;
import main.validators.OrderValidator;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final IRepository<Integer, Order<Integer>> orderRepository;
    private final IRepository<Integer, BirthdayCake<Integer>> cakeRepository;
    private final OrderValidator<Integer> orderValidator;
    private Integer currentOrderId;

    public OrderService(IRepository<Integer, Order<Integer>> orderRepository, IRepository<Integer, BirthdayCake<Integer>> cakeRepository) {
        this.orderRepository = orderRepository;
        this.currentOrderId = ((List<Order<Integer>>)orderRepository.findAll()).size() + 1;

        this.cakeRepository = cakeRepository;
        this.orderValidator = new OrderValidator<>();
    }

    public Integer placeOrder(Integer cakeId, String customerName, int quantity) {
        if (cakeRepository.findById(cakeId).isEmpty()) {
            throw new IllegalArgumentException("Cake with ID " + cakeId + " does not exist.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Order<Integer> order = new Order<>(cakeId, customerName, quantity);
        orderValidator.validate(order);
        order.setId(currentOrderId++);
        orderRepository.add(order);
        return order.getId();
    }

    public Iterable<Order<Integer>> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order<Integer>> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public void cancelOrder(Integer orderId) {
        Optional<Order<Integer>> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.");
        }

        Order<Integer> order = orderOpt.get();
        order.setStatus(Order.CANCELLED);
        orderValidator.validate(order);
        orderRepository.modify(order);
    }

    public void finishOrder(Integer orderId) {
        Optional<Order<Integer>> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.");
        }

        Order<Integer> order = orderOpt.get();
        order.setStatus(Order.FINISHED);
        orderValidator.validate(order);
        orderRepository.modify(order);
    }

    public void deleteOrder(Integer orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.");
        }

        orderRepository.delete(orderId);
    }

    public Iterable<Order<Integer>> filterByQuantity(int minQuantity, int maxQuantity) {
        validateQuantityRange(minQuantity, maxQuantity);

        FilterOrderByQuantity<Integer> quantityFilter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);
        InMemoryFilteredRepository<Integer, Order<Integer>> filteredRepository =
                new InMemoryFilteredRepository<>(orderRepository, quantityFilter);

        return filteredRepository.findAll();
    }

    public Iterable<Order<Integer>> filterByStatus(String desiredStatus) {
        FilterOrderByStatus<Integer> statusFilter = new FilterOrderByStatus<>(desiredStatus);

        InMemoryFilteredRepository<Integer, Order<Integer>> filteredRepository =
                new InMemoryFilteredRepository<>(orderRepository, statusFilter);

        return filteredRepository.findAll();
    }

    private void validateQuantityRange(int minQuantity, int maxQuantity) {
        if (minQuantity < 0) {
            throw new IllegalArgumentException("Minimum quantity cannot be negative.");
        }

        if (maxQuantity < minQuantity) {
            throw new IllegalArgumentException("Maximum quantity must be greater than or equal to minimum quantity.");
        }
    }
}
