package main;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.*;
import main.service.BirthdayCakeService;
import main.service.OrderService;
import main.ui.CakeShopUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

public class CakeShopApplication {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("settings.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return;
        }

        String repositoryType = properties.getProperty("Repository", "inmemory");
        String cakesFileOrUrl = properties.getProperty("BirthdayCakes", "cakes.txt");
        String ordersFileOrUrl = properties.getProperty("Orders", "orders.txt");

        if (repositoryType.equalsIgnoreCase("database")) {
            cakesFileOrUrl = properties.getProperty("Location", "jdbc:sqlite:/Users/alex/Documents/facultate/MAP/lab4_tema/a4-Alextz307/CakeShop/data/cakeshop.db");
            ordersFileOrUrl = cakesFileOrUrl;
        }

        IRepository<Long, BirthdayCake<Long>> cakeRepository = RepositoryFactory.createCakeRepository(repositoryType, cakesFileOrUrl);
        IRepository<Long, Order<Long>> orderRepository = RepositoryFactory.createOrderRepository(repositoryType, ordersFileOrUrl);

        BirthdayCakeService<Long> cakeService = new BirthdayCakeService<>(cakeRepository);
        OrderService<Long> orderService = new OrderService<>(orderRepository, cakeRepository);

        if (repositoryType.equalsIgnoreCase("inmemory")) {
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
        }

        Function<String, Long> idConverter = Long::valueOf;
        CakeShopUI<Long> cakeShopUI = new CakeShopUI<>(cakeService, orderService, idConverter);
        cakeShopUI.start();
    }
}
