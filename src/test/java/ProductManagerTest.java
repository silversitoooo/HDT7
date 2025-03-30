

import org.example.Product;
import org.example.ProductManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ProductManagerTest {
    private ProductManager manager;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        manager = new ProductManager();
    }

    @Test
    public void testFindProductBySKU() throws IOException {
        // Create a test CSV file
        File csvFile = createTestCSVFile();

        // Load products from the test CSV
        manager.loadFromCSV(csvFile.getAbsolutePath());

        // Test finding products by SKU
        Product product = manager.findProductBySKU("ABC123");
        assertNotNull(product);
        assertEquals("ABC123", product.getSku());
        assertEquals("Test Product 1", product.getProductName());
        assertEquals(79.99, product.getPriceCurrent(), 0.001);

        // Test finding a non-existent product
        Product nonExistentProduct = manager.findProductBySKU("NONEXIST");
        assertNull(nonExistentProduct);
    }

    @Test
    public void testGetAllProductsSortedByPrice() throws IOException {
        // Create a test CSV file
        File csvFile = createTestCSVFile();

        // Load products from the test CSV
        manager.loadFromCSV(csvFile.getAbsolutePath());

        // Test ascending order
        List<Product> ascending = manager.getAllProductsSortedByPrice(true);
        assertEquals(3, ascending.size());
        assertEquals(59.99, ascending.get(0).getPriceCurrent(), 0.001);
        assertEquals(79.99, ascending.get(1).getPriceCurrent(), 0.001);
        assertEquals(129.99, ascending.get(2).getPriceCurrent(), 0.001);

        // Test descending order
        List<Product> descending = manager.getAllProductsSortedByPrice(false);
        assertEquals(3, descending.size());
        assertEquals(129.99, descending.get(0).getPriceCurrent(), 0.001);
        assertEquals(79.99, descending.get(1).getPriceCurrent(), 0.001);
        assertEquals(59.99, descending.get(2).getPriceCurrent(), 0.001);
    }

    private File createTestCSVFile() throws IOException {
        File csvFile = tempFolder.newFile("test_products.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write header
            writer.write("Category,Field1,Field2,Field3,Field4,Field5,SKU,Field7,Field8,PriceRetail,PriceCurrent,Field11,Field12,Field13,Field14,Field15,Field16,Field17,ProductName\n");
            // Write test products
            writer.write("Electronics,F1,F2,F3,F4,F5,ABC123,F7,F8,99.99,79.99,F11,F12,F13,F14,F15,F16,F17,Test Product 1\n");
            writer.write("Appliances,F1,F2,F3,F4,F5,XYZ789,F7,F8,149.99,129.99,F11,F12,F13,F14,F15,F16,F17,Test Product 2\n");
            writer.write("Electronics,F1,F2,F3,F4,F5,MNO456,F7,F8,69.99,59.99,F11,F12,F13,F14,F15,F16,F17,Test Product 3\n");
        }
        return csvFile;
    }
}