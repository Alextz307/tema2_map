package main;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.IRepository;
import main.repository.InMemoryBirthdayCakeRepository;
import main.repository.InMemoryOrderRepository;
import main.service.BirthdayCakeService;
import main.service.OrderService;
import main.ui.CakeShopUI;

import java.util.function.Function;

public class CakeShopApplication {
    public static void main(String[] args) {
        IRepository<Long, BirthdayCake<Long>> cakeRepository = new InMemoryBirthdayCakeRepository<>();
        IRepository<Long, Order<Long>> orderRepository = new InMemoryOrderRepository<>();

        BirthdayCakeService<Long> cakeService = new BirthdayCakeService<>(cakeRepository);
        OrderService<Long> orderService = new OrderService<>(orderRepository, cakeRepository);

        Long chocolateCakeId = cakeService.addCake(new BirthdayCake<>("Chocolate Delight", "Chocolate", 25.99, 3));
        Long vanillaCakeId = cakeService.addCake(new BirthdayCake<>("Vanilla Dream", "Vanilla", 20.99, 2));
        Long strawberryCakeId = cakeService.addCake(new BirthdayCake<>("Strawberry Heaven", "Strawberry", 23.99, 2));
        Long redVelvetCakeId = cakeService.addCake(new BirthdayCake<>("Red Velvet Bliss", "Red Velvet", 29.99, 4));
        Long lemonCakeId = cakeService.addCake(new BirthdayCake<>("Lemon Zest", "Lemon", 19.99, 2));

        orderService.placeOrder(chocolateCakeId, "John Doe", 2);
        orderService.placeOrder(vanillaCakeId, "Jane Smith", 1);
        orderService.placeOrder(strawberryCakeId, "Emily Johnson", 3);
        orderService.placeOrder(redVelvetCakeId, "Michael Brown", 1);
        orderService.placeOrder(lemonCakeId, "Alice White", 5);

        Function<String, Long> idConverter = Long::valueOf;
        CakeShopUI<Long> cakeShopUI = new CakeShopUI<>(cakeService, orderService, idConverter);

        cakeShopUI.start();
    }
}
