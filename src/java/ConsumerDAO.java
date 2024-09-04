import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The ConsumerDAO class provides methods to interact with the database table 'Purchases'
 * for managing consumer purchases.
 */
public class ConsumerDAO {
    private Connection connection;

    /**
     * Constructs a new ConsumerDAO with the given database connection.
     *
     * @param connection The database connection.
     */
    public ConsumerDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new purchase record to the 'Purchases' table.
     *
     * @param consumer The ConsumerPOJO object representing the purchase.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addPurchase(ConsumerPOJO consumer) {
        String query = "INSERT INTO purchases (ItemID, PurchaseDate, QuantityClaimed) " +
                       "VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE QuantityClaimed = QuantityClaimed + VALUES(QuantityClaimed)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, consumer.getItemId());
            statement.setTimestamp(2, new Timestamp(consumer.getPurchaseDate().getTime()));
            statement.setInt(3, consumer.getQuantityClaimed());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new purchase record to the 'Purchases' table or updates an existing record
     * if a record with the same item ID already exists.
     *
     * @param consumer The ConsumerPOJO object representing the purchase.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addOrUpdatePurchase(ConsumerPOJO consumer) {
        if (existsPurchaseForItem(consumer.getItemId())) {
            int currentQuantityClaimed = getQuantityClaimed(consumer.getItemId());
            int newQuantityClaimed = currentQuantityClaimed + consumer.getQuantityClaimed();
            return updateQuantityClaimed(consumer.getItemId(), newQuantityClaimed);
        } else {
            return addPurchase(consumer);
        }
    }

    /**
     * Checks if a purchase record already exists for the given item ID.
     *
     * @param itemId The ID of the item.
     * @return true if a purchase record exists, false otherwise.
     */
    public boolean existsPurchaseForItem(int itemId) {
        String query = "SELECT COUNT(*) AS count FROM purchases WHERE ItemID = ?";
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
     * Updates the quantity claimed for a purchase record with the given item ID.
     *
     * @param itemId          The ID of the item.
     * @param quantityClaimed The new quantity claimed.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateQuantityClaimed(int itemId, int quantityClaimed) {
        String query = "UPDATE purchases SET QuantityClaimed = ? WHERE ItemID = ?";
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
     * Retrieves the quantity claimed for a purchase record with the given item ID.
     *
     * @param itemId The ID of the item.
     * @return The quantity claimed.
     */
    public int getQuantityClaimed(int itemId) {
        int quantityClaimed = 0;
        String query = "SELECT QuantityClaimed FROM purchases WHERE ItemID = ?";
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
