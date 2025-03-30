

import org.example.Product;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
    private Product product1;
    private Product product2;
    private Product product3;

    @Before
    public void setUp() {
        product1 = new Product("ABC123", 99.99, 79.99, "Test Product 1", "Electronics");
        product2 = new Product("XYZ789", 149.99, 129.99, "Test Product 2", "Appliances");
        product3 = new Product("ABC123", 89.99, 69.99, "Test Product 3", "Electronics");
    }

    @Test
    public void testGetters() {
        assertEquals("ABC123", product1.getSku());
        assertEquals(99.99, product1.getPriceRetail(), 0.001);
        assertEquals(79.99, product1.getPriceCurrent(), 0.001);
        assertEquals("Test Product 1", product1.getProductName());
        assertEquals("Electronics", product1.getCategory());
    }

    @Test
    public void testCompareToEquals() {
        // Same SKU should be considered equal
        assertEquals(0, product1.compareTo(product3));
    }

    @Test
    public void testCompareToDifferent() {
        // Different SKUs should be ordered lexicographically
        assertTrue(product1.compareTo(product2) < 0); // ABC123 comes before XYZ789
        assertTrue(product2.compareTo(product1) > 0); // XYZ789 comes after ABC123
    }

    @Test
    public void testToString() {
        String expected = "Product{sku='ABC123', name='Test Product 1', price=79.99}";
        assertEquals(expected, product1.toString());
    }
}