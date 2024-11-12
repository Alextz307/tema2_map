package main.ui;

import main.service.OrderService;

import java.util.Scanner;
import java.util.function.Function;

public class OrderManagementUI {
    private final OrderService orderService;
    private final Scanner scanner;

    public OrderManagementUI(OrderService orderService) {
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void manageOrders() {
        while (true) {
            System.out.println("\nOrder Management:");
            System.out.println("1. Place New Order");
            System.out.println("2. View All Orders");
            System.out.println("3. Cancel Order");
            System.out.println("4. Finish Order");
            System.out.println("5. Delete Order");
            System.out.println("6. Filter Orders by Quantity");
            System.out.println("7. Filter Orders by Status");
            System.out.println("8. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewAllOrders();
                    break;
                case 3:
                    cancelOrder();
                    break;
                case 4:
                    finishOrder();
                    break;
                case 5:
                    deleteOrder();
                    break;
                case 6:
                    filterOrdersByQuantity();
                    break;
                case 7:
                    filterOrdersByStatus();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void placeOrder() {
        System.out.print("Enter Cake ID: ");
        Integer cakeId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        try {
            orderService.placeOrder(cakeId, customerName, quantity);
            System.out.println("Order placed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    private void viewAllOrders() {
        orderService.getAllOrders().forEach(System.out::println);
    }

    private void cancelOrder() {
        System.out.print("Enter Order ID to cancel: ");
        Integer orderId = Integer.parseInt(scanner.nextLine());

        try {
            orderService.cancelOrder(orderId);
            System.out.println("Order cancelled successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error cancelling order: " + e.getMessage());
        }
    }

    private void finishOrder() {
        System.out.print("Enter Order ID to finish: ");
        Integer orderId = Integer.parseInt(scanner.nextLine());

        try {
            orderService.finishOrder(orderId);
            System.out.println("Order finished successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error finishing order: " + e.getMessage());
        }
    }

    private void deleteOrder() {
        System.out.print("Enter Order ID to delete: ");
        Integer orderId = Integer.parseInt(scanner.nextLine());

        try {
            orderService.deleteOrder(orderId);
            System.out.println("Order deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }

    private void filterOrdersByQuantity() {
        System.out.print("Enter minimum quantity: ");
        int minQuantity = scanner.nextInt();
        System.out.print("Enter maximum quantity: ");
        int maxQuantity = scanner.nextInt();
        orderService.filterByQuantity(minQuantity, maxQuantity).forEach(System.out::println);
    }

    private void filterOrdersByStatus() {
        System.out.print("Enter desired status (e.g., 'Pending', 'Finished', 'Cancelled'): ");
        String status = scanner.nextLine();
        orderService.filterByStatus(status).forEach(System.out::println);
    }
}
