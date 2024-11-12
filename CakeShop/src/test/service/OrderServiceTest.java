package test.service;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.memory.InMemoryRepository;
import main.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private InMemoryRepository<Long, Order<Long>> orderRepository;
    private InMemoryRepository<Long, BirthdayCake<Long>> cakeRepository;
    private OrderService<Long> orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryRepository<>();
        cakeRepository = new InMemoryRepository<>();
        orderService = new OrderService<>(orderRepository, cakeRepository);
    }

    @Test
    void placeOrder_ShouldPlaceOrder_WhenCakeExistsAndQuantityIsValid() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Chocolate Delight", "Chocolate", 20.0, 2));

        Long orderId = orderService.placeOrder(cakeId, "John Doe", 2);
        assertNotNull(orderId);
        assertTrue(orderRepository.findById(orderId).isPresent());
    }

    @Test
    void placeOrder_ShouldThrowException_WhenCakeDoesNotExist() {
        Long nonExistentCakeId = 999L;
        assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(nonExistentCakeId, "Jane Doe", 1));
    }

    @Test
    void placeOrder_ShouldThrowException_WhenQuantityIsInvalid() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Vanilla Dream", "Vanilla", 15.0, 1));
        assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(cakeId, "John Doe", 0));
    }

    @Test
    void cancelOrder_ShouldUpdateStatusToCancelled_WhenOrderExists() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Red Velvet", "Red Velvet", 25.0, 3));
        Long orderId = orderService.placeOrder(cakeId, "Alice", 2);

        orderService.cancelOrder(orderId);
        assertEquals(Order.CANCELLED, orderRepository.findById(orderId).get().getStatus());
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenOrderDoesNotExist() {
        Long nonExistentOrderId = 999L;
        assertThrows(IllegalArgumentException.class, () -> orderService.cancelOrder(nonExistentOrderId));
    }

    @Test
    void finishOrder_ShouldUpdateStatusToFinished_WhenOrderExists() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Lemon Twist", "Lemon", 18.0, 2));
        Long orderId = orderService.placeOrder(cakeId, "Bob", 1);

        orderService.finishOrder(orderId);
        assertEquals(Order.FINISHED, orderRepository.findById(orderId).get().getStatus());
    }

    @Test
    void deleteOrder_ShouldDeleteOrder_WhenOrderExists() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Blueberry Bliss", "Blueberry", 22.0, 3));
        Long orderId = orderService.placeOrder(cakeId, "Alice", 2);

        orderService.deleteOrder(orderId);
        assertFalse(orderRepository.findById(orderId).isPresent());
    }

    @Test
    void deleteOrder_ShouldThrowException_WhenOrderDoesNotExist() {
        Long nonExistentOrderId = 999L;
        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrder(nonExistentOrderId));
    }

    @Test
    void filterByQuantity_ShouldReturnOrdersWithinQuantityRange() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Rainbow Delight", "Fruit", 25.0, 3));
        orderService.placeOrder(cakeId, "Alice", 2);
        orderService.placeOrder(cakeId, "Bob", 5);

        Iterable<Order<Long>> filteredOrders = orderService.filterByQuantity(1, 3);
        filteredOrders.forEach(order -> assertTrue(order.getQuantity() >= 1 && order.getQuantity() <= 3));
    }

    @Test
    void filterByStatus_ShouldReturnOrdersWithDesiredStatus() {
        Long cakeId = cakeRepository.add(new BirthdayCake<>(null, "Choco Lava", "Chocolate", 30.0, 2));
        Long orderId = orderService.placeOrder(cakeId, "John", 3);
        orderService.cancelOrder(orderId);

        Iterable<Order<Long>> filteredOrders = orderService.filterByStatus(Order.CANCELLED);
        filteredOrders.forEach(order -> assertEquals(Order.CANCELLED, order.getStatus()));
    }
}
