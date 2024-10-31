package main.service;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.filter.FilterOrderByQuantity;
import main.filter.FilterOrderByStatus;
import main.repository.IRepository;
import main.repository.InMemoryFilteredRepository;

import java.util.Optional;

public class OrderService<ID> {
    private final IRepository<ID, Order<ID>> orderRepository;
    private final IRepository<ID, BirthdayCake<ID>> cakeRepository;

    public OrderService(IRepository<ID, Order<ID>> orderRepository, IRepository<ID, BirthdayCake<ID>> cakeRepository) {
        this.orderRepository = orderRepository;
        this.cakeRepository = cakeRepository;
    }

    public ID placeOrder(ID cakeId, String customerName, int quantity) {
        if (cakeRepository.findById(cakeId).isEmpty()) {
            throw new IllegalArgumentException("Cake with ID " + cakeId + " does not exist.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Order<ID> order = new Order<>(cakeId, customerName, quantity);
        return orderRepository.add(order);
    }

    public Iterable<Order<ID>> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order<ID>> getOrderById(ID id) {
        return orderRepository.findById(id);
    }

    public void cancelOrder(ID orderId) {
        Optional<Order<ID>> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.");
        }

        Order<ID> order = orderOpt.get();
        order.setStatus(Order.CANCELLED);
        orderRepository.modify(order);
    }

    public void finishOrder(ID orderId) {
        Optional<Order<ID>> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.");
        }

        Order<ID> order = orderOpt.get();
        order.setStatus(Order.FINISHED);
        orderRepository.modify(order);
    }

    public void deleteOrder(ID orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.");
        }

        orderRepository.delete(orderId);
    }

    public Iterable<Order<ID>> filterByQuantity(int minQuantity, int maxQuantity) {
        FilterOrderByQuantity<ID> quantityFilter = new FilterOrderByQuantity<>(minQuantity, maxQuantity);

        InMemoryFilteredRepository<ID, Order<ID>> filteredRepository =
                new InMemoryFilteredRepository<>(orderRepository, quantityFilter);

        return filteredRepository.findAll();
    }

    public Iterable<Order<ID>> filterByStatus(String desiredStatus) {
        FilterOrderByStatus<ID> statusFilter = new FilterOrderByStatus<>(desiredStatus);

        InMemoryFilteredRepository<ID, Order<ID>> filteredRepository =
                new InMemoryFilteredRepository<>(orderRepository, statusFilter);

        return filteredRepository.findAll();
    }
}
