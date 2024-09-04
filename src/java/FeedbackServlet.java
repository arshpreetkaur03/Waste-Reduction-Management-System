import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for handling feedback submissions.
 */
@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Redirects GET requests to the feedback form page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to the feedback form page
        response.sendRedirect("feedback.html");
    }

    /**
     * Handles POST requests containing feedback submissions.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String UserID = request.getParameter("UserID");
        String itemIDStr = request.getParameter("itemID");
        String feedbackText = request.getParameter("feedback_text");
        String ratingStr = request.getParameter("rating");

        if (UserID == null || itemIDStr == null || feedbackText == null || ratingStr == null ||
                UserID.isEmpty() || itemIDStr.isEmpty() || feedbackText.isEmpty() || ratingStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters.");
            return;
        }

        int itemID;
        int rating;
        try {
            itemID = Integer.parseInt(itemIDStr);
            rating = Integer.parseInt(ratingStr);
            if (rating < 1 || rating > 5) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters.");
            return;
        }

        // Check if user id and item id exist in the database
        try {
            if (!doesUserExist(Integer.parseInt(UserID)) || !doesItemExist(itemID)) {
                response.sendRedirect("feedbackFail.jsp");
                return;
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        try {
            saveFeedback(Integer.parseInt(UserID), itemID, feedbackText, rating, formattedDate);
            response.sendRedirect("feedbackSuccess.jsp"); // Redirect to a success page
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    /**
     * Checks if a user exists in the database.
     * 
     * @param userID The ID of the user to check.
     * @return true if the user exists, false otherwise.
     * @throws SQLException if a database error occurs.
     */
    private boolean doesUserExist(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE UserID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Checks if an item exists in the database.
     * 
     * @param itemID The ID of the item to check.
     * @return true if the item exists, false otherwise.
     * @throws SQLException if a database error occurs.
     */
    private boolean doesItemExist(int itemID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Inventory WHERE ItemID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Saves feedback data to the database.
     * 
     * @param userID The ID of the user submitting the feedback.
     * @param itemID The ID of the item being rated.
     * @param feedbackText The text of the feedback.
     * @param rating The rating given by the user.
     * @param feedbackDate The date and time the feedback was submitted.
     * @throws SQLException if a database error occurs.
     */
    private void saveFeedback(int userID, int itemID, String feedbackText, int rating, String feedbackDate) throws SQLException {
        String sql = "INSERT INTO Feedback (UserID, ItemID, feedback_text, rating, feedback_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            statement.setInt(2, itemID);
            statement.setString(3, feedbackText);
            statement.setInt(4, rating);
            statement.setString(5, feedbackDate);
            statement.executeUpdate();
        }
    }
}