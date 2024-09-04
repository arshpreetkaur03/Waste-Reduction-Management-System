import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SubscriptionServlet")
public class SubscriptionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int userID = generateOrRetrieveUserID(request.getParameter("email")); // Retrieve UserID
        
        // Retrieve subscription details from the request parameters
        String email = request.getParameter("email");
        String communicationMethod = request.getParameter("communicationMethod");
        String location = request.getParameter("location");
        String foodPreferences = request.getParameter("foodPreferences");
    
        // Insert subscription details into the database
        try (Connection connection = DBConnection.getConnection()) {
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO(connection);
            boolean success = subscriptionDAO.addSubscription(userID, email, communicationMethod, location, foodPreferences);
            if (success) {
                response.sendRedirect("Subscription.jsp");
            } else {
                response.sendRedirect("SubscriptionServlet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("SubscriptionServlet"); // Redirect to an error page
        }
    }
    
    private int generateOrRetrieveUserID(String email) {
        int userID = 0;
        try (Connection connection = DBConnection.getConnection()) {
            // Query to retrieve UserID based on the entered email address
            String query = "SELECT UserID FROM Users WHERE Email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the email parameter for the query
                statement.setString(1, email);
                
                // Execute the query
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // If a result is found, retrieve the UserID
                    userID = resultSet.getInt("UserID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return userID;
    }
}
