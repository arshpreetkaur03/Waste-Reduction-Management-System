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

import transferObject.UserDTO;
import transferObject.UserType;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String userTypeString = request.getParameter("userType");
        UserType userType = null;

        if (userTypeString != null) {
            userType = UserType.valueOf(userTypeString.replace(" ", "_"));
        } else {
            userType = UserType.Consumer;
        }

        UserDTO user = new UserDTO(name, email, password, userType);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();

            // Insert user data into Users table and get the generated UserId
            String userSql = "INSERT INTO Users (name, email, password, userType) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(userSql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUserType().name());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Get the generated UserId
            resultSet = statement.getGeneratedKeys();
            int userId = -1;
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        switch (userType) {
            case Retailer:
                request.getRequestDispatcher("/InventoryItem.jsp").forward(request, response);
                break;
            case Charitable_Organization:
                request.getRequestDispatcher("/ClaimInformation.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/Consumer.jsp").forward(request, response);
                break;
        }
    }
}
