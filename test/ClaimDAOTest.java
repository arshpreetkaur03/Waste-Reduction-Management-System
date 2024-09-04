import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClaimDAOTest {

    private Connection connection;
    private ClaimDAO claimDAO;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize your database connection here. This could be a static method in a utility class.
        connection = DBConnection.getConnection();
        claimDAO = new ClaimDAO(connection);

        // Optional: Clean up the database or set it to a known state before each test
    }

    @Test
    public void testAddClaim() throws SQLException {
        // Assuming the claim does not exist, this should add it
        ClaimPOJO claim = new ClaimPOJO(2, new Date(), 5);
        boolean addResult = claimDAO.addClaim(claim);
        assertTrue(addResult, "Initial claim should be added.");
    }

    @Test
    public void testAddOrUpdateClaim() throws SQLException {
        // Assuming the claim does not exist, this should add it
        ClaimPOJO claim = new ClaimPOJO(3, new Date(), 5);
        boolean addResult = claimDAO.addOrUpdateClaim(claim);
        assertTrue(addResult, "Initial claim should be added.");

        // Now, update the claim
        claim.setQuantityClaimed(15);
        boolean updateResult = claimDAO.addOrUpdateClaim(claim);
        assertTrue(updateResult, "Claim should be updated successfully.");
    }

    @Test
    public void testExistsClaimForItem() throws SQLException {
        ClaimPOJO claim = new ClaimPOJO(4, new Date(), 5);
        claimDAO.addClaim(claim); // Ensure there's a claim to find
        boolean exists = claimDAO.existsClaimForItem(4);
        assertTrue(exists, "Claim should exist for item.");
    }

    @Test
    public void testUpdateQuantityClaimed() throws SQLException {
        ClaimPOJO claim = new ClaimPOJO(5, new Date(), 10);
        claimDAO.addClaim(claim); // Ensure the claim exists
        boolean updated = claimDAO.updateQuantityClaimed(5, 20);
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
