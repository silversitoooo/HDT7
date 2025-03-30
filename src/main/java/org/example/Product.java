package org.example;

/**
 * Product class to represent each product
 */
public class Product implements Comparable<Product> {
    private String sku;
    private double priceRetail;
    private double priceCurrent;
    private String productName;
    private String category;

    public Product(String sku, double priceRetail, double priceCurrent, String productName, String category) {
        this.sku = sku;
        this.priceRetail = priceRetail;
        this.priceCurrent = priceCurrent;
        this.productName = productName;
        this.category = category;
    }

    // Getters
    public String getSku() { return sku; }
    public double getPriceRetail() { return priceRetail; }
    public double getPriceCurrent() { return priceCurrent; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }

    // Compare products by SKU for default BST ordering
    @Override
    public int compareTo(Product other) {
        return this.sku.compareTo(other.sku);
    }

    // For debugging
    @Override
    public String toString() {
        return "Product{sku='" + sku + "', name='" + productName + "', price=" + priceCurrent + "}";
    }
}