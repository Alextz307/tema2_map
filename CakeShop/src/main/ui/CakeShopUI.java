package main.ui;

import main.service.BirthdayCakeService;
import main.service.OrderService;

import java.util.Scanner;
import java.util.function.Function;

public class CakeShopUI<ID> {
    private final CakeManagementUI<ID> cakeManagementUI;
    private final OrderManagementUI<ID> orderManagementUI;
    private final Scanner scanner;

    public CakeShopUI(BirthdayCakeService<ID> cakeService, OrderService<ID> orderService, Function<String, ID> idConverter) {
        this.cakeManagementUI = new CakeManagementUI<>(cakeService, idConverter);
        this.orderManagementUI = new OrderManagementUI<>(orderService, idConverter);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\nCake Shop Menu:");
            System.out.println("1. Manage Cakes");
            System.out.println("2. Manage Orders");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    cakeManagementUI.manageCakes();
                    break;
                case 2:
                    orderManagementUI.manageOrders();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
