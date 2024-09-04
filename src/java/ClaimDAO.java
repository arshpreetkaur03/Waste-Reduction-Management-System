import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ClaimDAO {
    private  Connection connection;

    public ClaimDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new claim record to the 'Claims' table.
     *
     * @param claim The ClaimPOJO object representing the claim.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addClaim(ClaimPOJO claim) {
        String query = "INSERT INTO Claims (ItemID, ClaimDate, QuantityClaimed) " +
                       "VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, claim.getItemId());
            statement.setTimestamp(2, new Timestamp(claim.getClaimDate().getTime()));
            statement.setInt(3, claim.getQuantityClaimed());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new claim record to the 'Claims' table or updates an existing record
     * if a record with the same item ID already exists.
     *
     * @param claim The ClaimPOJO object representing the claim.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addOrUpdateClaim(ClaimPOJO claim) {
        if (existsClaimForItem(claim.getItemId())) {
            int currentQuantityClaimed = getQuantityClaimed(claim.getItemId());
            int newQuantityClaimed = currentQuantityClaimed + claim.getQuantityClaimed();
            return updateQuantityClaimed(claim.getItemId(), newQuantityClaimed);
        } else {
            return addClaim(claim);
        }
    }

    /**
     * Checks if a claim record already exists for the given item ID.
     *
     * @param itemId The ID of the item.
     * @return true if a claim record exists, false otherwise.
     */
    public boolean existsClaimForItem(int itemId) {
        String query = "SELECT COUNT(*) AS count FROM Claims WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the quantity claimed for a claim record with the given item ID.
     *
     * @param itemId          The ID of the item.
     * @param quantityClaimed The new quantity claimed.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateQuantityClaimed(int itemId, int quantityClaimed) {
        String query = "UPDATE Claims SET QuantityClaimed = ? WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantityClaimed);
            preparedStatement.setInt(2, itemId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the quantity claimed for a claim record with the given item ID.
     *
     * @param itemId The ID of the item.
     * @return The quantity claimed.
     */
    public int getQuantityClaimed(int itemId) {
        int quantityClaimed = 0;
        String query = "SELECT QuantityClaimed FROM Claims WHERE ItemID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    quantityClaimed = resultSet.getInt("QuantityClaimed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantityClaimed;
    }
}