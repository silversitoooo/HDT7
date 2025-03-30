

import org.example.BST;
import org.example.Product;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class BSTTest {
    private BST<Integer> bst;
    private BST<Product> productBst;

    @Before
    public void setUp() {
        bst = new BST<>();
        productBst = new BST<>();
    }

    @Test
    public void testInsertAndSearch() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(2);
        bst.insert(4);

        assertEquals(Integer.valueOf(5), bst.search(5));
        assertEquals(Integer.valueOf(3), bst.search(3));
        assertEquals(Integer.valueOf(7), bst.search(7));
        assertEquals(Integer.valueOf(2), bst.search(2));
        assertEquals(Integer.valueOf(4), bst.search(4));

        // Test searching for non-existent element
        assertNull(bst.search(10));
    }

    @Test
    public void testInOrderTraversal() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(2);
        bst.insert(4);

        List<Integer> result = new ArrayList<>();
        bst.inOrderTraversal(result);

        // In-order traversal should return sorted elements
        List<Integer> expected = List.of(2, 3, 4, 5, 7);
        assertEquals(expected, result);
    }

    @Test
    public void testProductBST() {
        Product p1 = new Product("ABC123", 99.99, 79.99, "Test Product 1", "Electronics");
        Product p2 = new Product("XYZ789", 149.99, 129.99, "Test Product 2", "Appliances");
        Product p3 = new Product("MNO456", 89.99, 69.99, "Test Product 3", "Electronics");

        productBst.insert(p1);
        productBst.insert(p2);
        productBst.insert(p3);

        // Find products by SKU
        Product searchDummy1 = new Product("ABC123", 0, 0, "", "");
        Product foundP1 = productBst.search(searchDummy1);
        assertEquals(p1, foundP1);

        Product searchDummy2 = new Product("XYZ789", 0, 0, "", "");
        Product foundP2 = productBst.search(searchDummy2);
        assertEquals(p2, foundP2);

        // Test in-order traversal for products (should be sorted by SKU)
        List<Product> result = new ArrayList<>();
        productBst.inOrderTraversal(result);
        assertEquals(3, result.size());
        assertEquals("ABC123", result.get(0).getSku());
        assertEquals("MNO456", result.get(1).getSku());
        assertEquals("XYZ789", result.get(2).getSku());
    }
}