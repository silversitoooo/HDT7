package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class
 */
public class ProductSearch {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        Scanner scanner = new Scanner(System.in);

        try {
            // Load products from CSV
            System.out.println("Loading products from CSV...");
            manager.loadFromCSV("C:\\Users\\ariel\\OneDrive\\Desktop\\HDT7\\HDT7\\src\\main\\java\\home appliance skus lowes.csv");  // Adjust path as needed

            boolean running = true;

            while (running) {
                System.out.println("\n=== Product Search System ===");
                System.out.println("1. Search by SKU");
                System.out.println("2. List all products (price ascending)");
                System.out.println("3. List all products (price descending)");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.print("Enter SKU to search: ");
                        String sku = scanner.nextLine().trim();
                        Product product = manager.findProductBySKU(sku);
                        manager.displayProductDetails(product);
                        break;

                    case "2":
                        System.out.println("\nProducts sorted by price (ascending):");
                        listProducts(manager.getAllProductsSortedByPrice(true));
                        break;

                    case "3":
                        System.out.println("\nProducts sorted by price (descending):");
                        listProducts(manager.getAllProductsSortedByPrice(false));
                        break;

                    case "4":
                        running = false;
                        System.out.println("Exiting program. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error loading products: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Helper method to display product lists
    private static void listProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%s - %s - $%.2f (Retail: $%.2f) - %s%n",
                    p.getSku(), p.getProductName(), p.getPriceCurrent(),
                    p.getPriceRetail(), p.getCategory());

            // Add a break after 20 products and ask if user wants to see more
            if ((i + 1) % 20 == 0 && i + 1 < products.size()) {
                System.out.print("Press Enter to see more products (or type 'q' to return to menu): ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
    }
}