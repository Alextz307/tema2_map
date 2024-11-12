package main.ui;

import main.domain.BirthdayCake;
import main.service.BirthdayCakeService;

import java.util.Scanner;
import java.util.function.Function;

public class CakeManagementUI {
    private final BirthdayCakeService cakeService;
    private final Scanner scanner;

    public CakeManagementUI(BirthdayCakeService cakeService) {
        this.cakeService = cakeService;
        this.scanner = new Scanner(System.in);
    }

    public void manageCakes() {
        while (true) {
            System.out.println("\nCake Management:");
            System.out.println("1. Add Cake");
            System.out.println("2. View All Cakes");
            System.out.println("3. Update Cake");
            System.out.println("4. Delete Cake");
            System.out.println("5. Filter Cakes by Flavor");
            System.out.println("6. Filter Cakes by Price Range");
            System.out.println("7. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCake();
                    break;
                case 2:
                    viewAllCakes();
                    break;
                case 3:
                    updateCake();
                    break;
                case 4:
                    deleteCake();
                    break;
                case 5:
                    filterCakesByFlavor();
                    break;
                case 6:
                    filterCakesByPriceRange();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addCake() {
        System.out.print("Enter Cake Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Cake Flavor: ");
        String flavor = scanner.nextLine();
        System.out.print("Enter Cake Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Number of Layers: ");
        int layers = scanner.nextInt();

        try {
            cakeService.addCake(new BirthdayCake<>(name, flavor, price, layers));
            System.out.println("Cake added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding cake: " + e.getMessage());
        }
    }

    private void viewAllCakes() {
        cakeService.getAllCakes().forEach(System.out::println);
    }

    private void updateCake() {
        System.out.print("Enter Cake ID to update: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter New Cake Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Cake Flavor: ");
        String flavor = scanner.nextLine();
        System.out.print("Enter New Cake Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter New Number of Layers: ");
        int layers = scanner.nextInt();

        try {
            cakeService.updateCake(new BirthdayCake<>(id, name, flavor, price, layers));
            System.out.println("Cake updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating cake: " + e.getMessage());
        }
    }

    private void deleteCake() {
        System.out.print("Enter Cake ID to delete: ");
        Integer id = Integer.parseInt(scanner.nextLine());

        try {
            cakeService.deleteCake(id);
            System.out.println("Cake deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting cake: " + e.getMessage());
        }
    }

    private void filterCakesByFlavor() {
        System.out.print("Enter desired flavor: ");
        String flavor = scanner.nextLine();
        cakeService.filterByFlavor(flavor).forEach(System.out::println);
    }

    private void filterCakesByPriceRange() {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        cakeService.filterByPriceRange(minPrice, maxPrice).forEach(System.out::println);
    }
}
