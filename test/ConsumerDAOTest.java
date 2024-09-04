import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsumerDAOTest {

    private Connection connection;
    private ConsumerDAO consumerDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize your database connection here. This could be a static method in a utility class.
        connection = DBConnection.getConnection();
        consumerDAO = new ConsumerDAO(connection);

        // Optional: Clean up the database or set it to a known state before each test
    }

    

    @Test
    public void testAddOrUpdatePurchase() throws SQLException {
        // Assuming the item does not exist, this should add it
        ConsumerPOJO consumer = new ConsumerPOJO(2, new Date(), 5);
        boolean addResult = consumerDAO.addOrUpdatePurchase(consumer);
        assertTrue(addResult, "Initial purchase should be added.");

        // Now, update the purchase
        consumer.setQuantityClaimed(15);
        boolean updateResult = consumerDAO.addOrUpdatePurchase(consumer);
        assertTrue(updateResult, "Purchase should be updated successfully.");
    }

    @Test
    public void testExistsPurchaseForItem() throws SQLException {
        ConsumerPOJO consumer = new ConsumerPOJO(3, new Date(), 5);
        consumerDAO.addPurchase(consumer); // Ensure there's a purchase to find
        boolean exists = consumerDAO.existsPurchaseForItem(3);
        assertTrue(exists, "Purchase should exist for item.");
    }

    @Test
    public void testUpdateQuantityClaimed() throws SQLException {
        ConsumerPOJO consumer = new ConsumerPOJO(4, new Date(), 10);
        consumerDAO.addPurchase(consumer); // Ensure the purchase exists
        boolean updated = consumerDAO.updateQuantityClaimed(4, 20);
        assertTrue(updated, "Quantity claimed should be updated successfully.");
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Optional: Clean up the database after each test

        if (connection != null) {
            connection.close();
        }
    }
}