import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object (DAO) for managing inventory items.
 */
public class InventoryDAO {
    // SQL queries
    private static final String SELECT_INVENTORY_BY_ITEM_ID = "SELECT * FROM Inventory WHERE UserID = ?";
    private static final String INSERT_INVENTORY_ITEM = "INSERT INTO Inventory (ItemName, Quantity, ExpirationDate, RetailerPrice, IsDonation, IsSale, DiscountPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_INVENTORY_ITEM = "UPDATE Inventory SET ItemName = ?, Quantity = ?, ExpirationDate = ?, RetailerPrice = ?, IsDonation = ?, IsSale = ?, DiscountPrice = ? WHERE ItemID = ?";
    private static final String DELETE_INVENTORY_ITEM = "DELETE FROM Inventory WHERE ItemID = ?";
    private static final String VIEW_SURPLUS_INVENTORY_ITEM = "SELECT * FROM Inventory WHERE IsDonation = 1 OR IsSale = 1";
    private static final String VIEW_Donation_INVENTORY_ITEM = "SELECT * FROM Inventory WHERE IsDonation = 1 ";

    /**
     * Retrieves all inventory items.
     *
     * @return List of inventory items.
     */
    public List<InventoryItem> getInventory() {
        List<InventoryItem> inventory = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INVENTORY_BY_ITEM_ID);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                InventoryItem item = new InventoryItem();
                item.setItemId(resultSet.getInt("ItemID"));
                item.setItemName(resultSet.getString("ItemName"));
                item.setQuantity(resultSet.getInt("Quantity"));
                item.setExpirationDate(resultSet.getDate("ExpirationDate"));
                item.setRetailerPrice(resultSet.getDouble("RetailerPrice"));
                item.setDonation(resultSet.getBoolean("IsDonation"));
                item.setSale(resultSet.getBoolean("IsSale"));
                item.setDiscountPrice(resultSet.getDouble("DiscountPrice"));
                inventory.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    /**
     * Adds a new item to the inventory.
     *
     * @param item The inventory item to add.
     * @return true if the item was successfully added, false otherwise.
     */
    public boolean addItemToInventory(InventoryItem item) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INVENTORY_ITEM)) {
            preparedStatement.setString(1, item.getItemName());
            preparedStatement.setInt(2, item.getQuantity());
            preparedStatement.setDate(3, new java.sql.Date(item.getExpirationDate().getTime()));
            preparedStatement.setDouble(4, item.getRetailerPrice());
            preparedStatement.setBoolean(5, item.isDonation());
            preparedStatement.setBoolean(6, item.isSale());
            preparedStatement.setDouble(7, item.getDiscountPrice());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing inventory item.
     *
     * @param itemId             The ID of the item to update.
     * @param newItemName        The new name of the item.
     * @param newQuantity        The new quantity of the item.
     * @param newExpirationDate  The new expiration date of the item.
     * @param newRetailerPrice   The new retailer price of the item.
     * @param newIsDonation      The new donation status of the item.
     * @param newIsSale          The new sale status of the item.
     * @param newDiscountPrice   The new discount price of the item.
     * @return true if the item was successfully updated, false otherwise.
     */
    public boolean updateInventoryItem(int itemId, String newItemName, int newQuantity, Date newExpirationDate, double newRetailerPrice, boolean newIsDonation, boolean newIsSale, double newDiscountPrice) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INVENTORY_ITEM)) {
            preparedStatement.setString(1, newItemName);
            preparedStatement.setInt(2, newQuantity);
            preparedStatement.setDate(3, new java.sql.Date(newExpirationDate.getTime()));
            preparedStatement.setDouble(4, newRetailerPrice);
            preparedStatement.setBoolean(5, newIsDonation);
            preparedStatement.setBoolean(6, newIsSale);
            preparedStatement.setDouble(7, newDiscountPrice);
            preparedStatement.setInt(8, itemId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an inventory item by its ID.
     *
     * @param itemId The ID of the item to delete.
     * @return true if the item was successfully deleted, false otherwise.
     */
    public boolean deleteInventoryItem(int itemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INVENTORY_ITEM)) {
            preparedStatement.setInt(1, itemId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item with ID " + itemId + " deleted successfully.");
                return true;
            } else {
                System.out.println("No item found with ID " + itemId + ". No deletion performed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting item with ID " + itemId + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves donation inventory items.
     *
     * @return List of donation inventory items.
     */
    public List<InventoryItem> getDonationItems() {
        List<InventoryItem> donationItems = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(VIEW_Donation_INVENTORY_ITEM);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                InventoryItem item = new InventoryItem();
                item.setItemId(resultSet.getInt("ItemID"));
                item.setItemName(resultSet.getString("ItemName"));
                item.setQuantity(resultSet.getInt("Quantity"));
                item.setExpirationDate(resultSet.getDate("ExpirationDate"));
                item.setRetailerPrice(resultSet.getDouble("RetailerPrice"));
                item.setDonation(resultSet.getBoolean("IsDonation"));
                item.setSale(resultSet.getBoolean("IsSale"));
                item.setDiscountPrice(resultSet.getDouble("DiscountPrice"));
                donationItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donationItems;
    }
    
    

    /**
     * Flags an item in the inventory as surplus (donation or sale item).
     *
     * @param surplusItemId The ID of the item to flag as surplus.
     * @return true if the item was successfully flagged, false otherwise.
     */
    public boolean flagSurplusItem(int surplusItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(VIEW_SURPLUS_INVENTORY_ITEM)) {
            preparedStatement.setInt(1, surplusItemId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item with ID " + surplusItemId + " flagged successfully.");
                return true;
            } else {
                System.out.println("No item found with ID " + surplusItemId + ". No flagging performed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error flagging item with ID " + surplusItemId + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates the quantity of an inventory item.
     *
     * @param itemId     The ID of the item to update.
     * @param newQuantity The new quantity of the item.
     * @return true if the quantity was successfully updated, false otherwise.
     */
    public boolean updateInventoryItemQuantity(int itemId, int newQuantity) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Inventory SET Quantity = ? WHERE ItemID = ?")) {
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, itemId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Decreases the quantity of an inventory item by 1.
     *
     * @param itemId The ID of the item to decrease the quantity of.
     * @return true if the quantity was successfully decreased, false otherwise.
     */
   public boolean decreaseInventoryItemQuantity(int itemId) {
    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Inventory SET Quantity = Quantity - 1 WHERE ItemID = ? AND Quantity > 0")) {
        preparedStatement.setInt(1, itemId);

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        return false;
    }
    

}}
