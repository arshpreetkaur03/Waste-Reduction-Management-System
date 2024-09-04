import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class InventoryDAOTest {

    private InventoryDAO inventoryDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize connection to your test database
        connection = DBConnection.getConnection();
        inventoryDAO = new InventoryDAO();
        
        // Optional: Clear the Inventory table to start fresh
        // executeUpdate(connection, "DELETE FROM Inventory");
    }
    
    @Test
    public void testAddItemToInventory() throws Exception {
        InventoryItem newItem = new InventoryItem(0, "Test Item", 5, new Date(), 9.99, true, false, 0);
        boolean result = inventoryDAO.addItemToInventory(newItem);
        assertTrue(result, "The item should be added successfully.");
    }


    @Test
    public void testDeleteInventoryItem() throws Exception {
        // Assume an item exists with ItemID = 1
        boolean result = inventoryDAO.deleteInventoryItem(33);
        assertTrue(result, "The item should be deleted successfully.");
    }

   

    @AfterEach
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
    
    // Utility method for executing direct SQL updates during setup/teardown
    private void executeUpdate(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
}