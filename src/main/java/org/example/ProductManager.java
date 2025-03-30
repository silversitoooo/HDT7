package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Manager class to handle product operations
 */
public class ProductManager {
    private BST<Product> productTree;

    public ProductManager() {
        this.productTree = new BST<>();
    }

    // Load products from CSV
    public void loadFromCSV(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip header line
            String header = br.readLine();
            System.out.println("CSV Header: " + header);

            int lineCount = 0;
            int productCount = 0;
            int priceParseErrors = 0;

            while ((line = br.readLine()) != null) {
                lineCount++;

                // CSV parsing that handles quoted fields correctly
                List<String> fields = parseCSVLine(line);

                // Debug first few lines
                if (lineCount < 5) {
                    System.out.println("Line " + lineCount + " has " + fields.size() + " fields");
                    for (int i = 0; i < Math.min(fields.size(), 20); i++) {
                        System.out.println("Field " + i + ": " + fields.get(i));
                    }
                }

                try {
                    // Make sure we have enough fields
                    if (fields.size() >= 19) {  // Ensure we have enough fields for required data
                        // Correct indices based on the CSV structure
                        String sku = fields.get(6).trim();  // SKU is at index 6
                        String category = fields.get(0).trim();  // Category is at index 0
                        String productName = fields.get(18).trim();  // Product Name is at index 18

                        // Parse prices, handling empty values
                        double priceRetail = 0.0;
                        double priceCurrent = 0.0;

                        try {
                            String retailStr = fields.get(9).trim();
                            String currentStr = fields.get(10).trim();

                            if (!retailStr.isEmpty() && retailStr.matches("^\\d*\\.?\\d+$")) {
                                priceRetail = Double.parseDouble(retailStr);
                            }
                            if (!currentStr.isEmpty() && currentStr.matches("^\\d*\\.?\\d+$")) {
                                priceCurrent = Double.parseDouble(currentStr);
                            }
                        } catch (NumberFormatException e) {
                            priceParseErrors++;
                            if (priceParseErrors < 10) {
                                System.out.println("Invalid price format at line " + lineCount + ": " + e.getMessage());
                            }
                            continue;
                        }

                        Product product = new Product(sku, priceRetail, priceCurrent, productName, category);
                        productTree.insert(product);
                        productCount++;

                        if (productCount < 5) {
                            System.out.println("Added product: " + product);
                        }
                    } else {
                        System.out.println("Insufficient fields at line " + lineCount + ": found " + fields.size());
                    }
                } catch (Exception e) {
                    // Skip invalid entries
                    System.out.println("Skipping invalid entry at line " + lineCount + ": " + e.getMessage());
                }
            }

            System.out.println("Read " + lineCount + " lines from CSV");
            System.out.println("Added " + productCount + " products to BST");
            System.out.println("Price parse errors: " + priceParseErrors);
        }
    }

    // Better CSV parsing that handles quoted fields
    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());

        return result;
    }

    // Find a product by SKU
    public Product findProductBySKU(String sku) {
        // Create a dummy product with the target SKU for searching
        Product dummyProduct = new Product(sku, 0, 0, "", "");
        return productTree.search(dummyProduct);
    }

    // Get all products sorted by price
    public List<Product> getAllProductsSortedByPrice(boolean ascending) {
        List<Product> products = new ArrayList<>();
        productTree.inOrderTraversal(products);

        // Sort by price
        Comparator<Product> priceComparator = Comparator.comparing(Product::getPriceCurrent);
        if (!ascending) {
            priceComparator = priceComparator.reversed();
        }

        Collections.sort(products, priceComparator);
        return products;
    }

    // Display product details
    public void displayProductDetails(Product product) {
        if (product != null) {
            System.out.println("Product Details:");
            System.out.println("SKU: " + product.getSku());
            System.out.println("Name: " + product.getProductName());
            System.out.println("Category: " + product.getCategory());
            System.out.println("Retail Price: $" + product.getPriceRetail());
            System.out.println("Current Price: $" + product.getPriceCurrent());
        } else {
            System.out.println("Product not found.");
        }
    }
}