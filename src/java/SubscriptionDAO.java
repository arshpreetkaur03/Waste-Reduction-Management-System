import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubscriptionDAO {

    private Connection connection;

    public SubscriptionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addSubscription(int userID, String email, String communicationMethod, String location, String foodPreferences) {
        String query = "INSERT INTO Subscriptions (UserID, Email, CommunicationMethod, Location, FoodPreferences) " +
                       "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.setString(2, email);
            statement.setString(3, communicationMethod);
            statement.setString(4, location);
            statement.setString(5, foodPreferences);
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
