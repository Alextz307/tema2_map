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

        IRepository<Integer, BirthdayCake<Integer>> cakeRepository = RepositoryFactory.createCakeRepository(repositoryType, cakesFileOrUrl);
        IRepository<Integer, Order<Integer>> orderRepository = RepositoryFactory.createOrderRepository(repositoryType, ordersFileOrUrl);

        BirthdayCakeService cakeService = new BirthdayCakeService(cakeRepository);
        OrderService orderService = new OrderService(orderRepository, cakeRepository);

        if (repositoryType.equalsIgnoreCase("inmemory")) {
            cakeService.addCake(new BirthdayCake<>("Chocolate Delight", "Chocolate", 25.99, 3));
            cakeService.addCake(new BirthdayCake<>("Vanilla Dream", "Vanilla", 20.99, 2));
            cakeService.addCake(new BirthdayCake<>("Strawberry Heaven", "Strawberry", 23.99, 2));
            cakeService.addCake(new BirthdayCake<>("Red Velvet Bliss", "Red Velvet", 29.99, 4));
            cakeService.addCake(new BirthdayCake<>("Lemon Zest", "Lemon", 19.99, 2));

            orderService.placeOrder(1, "John Doe", 2);
            orderService.placeOrder(2, "Jane Smith", 1);
            orderService.placeOrder(3, "Emily Johnson", 3);
            orderService.placeOrder(4, "Michael Brown", 1);
            orderService.placeOrder(5, "Alice White", 5);
        }

        CakeShopUI cakeShopUI = new CakeShopUI(cakeService, orderService);
        cakeShopUI.start();
    }
}
