<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback View</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            background-image: url('./css/food.jpeg');
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #527a7a;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .header {
            background-color: #293d3d;
            color: #fff;
            padding: 20px;
            text-align: center;
            font-size: 24px;
        }

        .footer {
            background-color: #293d3d;
            color: #fff;
            padding: 20px;
            text-align: center;
            border-radius: 0 0 5px 5px;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
        .header-buttons {
    position: absolute;
    top: 50px;
    right: 20px;
}

.header-buttons a {
    text-decoration: none;
   background-color: #527a7a; /* Light gray button background */
    color: #fff; /* White text color */
    padding: 8px 15px; /* Adjusted button padding */
    border-radius: 4px;
    transition: background-color 0.3s ease;
    margin-left: 10px;
    font-size: 14px; /* Adjusted button font size */
}

.header-buttons a:hover {
    background-color: #bbb; /* Slightly darker shade on hover */
}

    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <h1>Feedback View</h1>
        <div class="header-buttons">
            <a href="LoginServlet" class="button">Logout</a>
            
</div>
        
    </div>

    <!-- Main content container -->
    <div class="container">
        <!-- Feedback table -->
        <h2>Feedback Details</h2>
        <table>
            <tr>
                <th>Feedback ID</th>
                <th>User ID</th>
                <th>Date</th>
                <th>Feedback</th>
                <th>Rating</th>
            </tr>
            <!-- Loop through feedback -->
            <%
                Connection connection = null;
                Statement st = null;
                ResultSet rs = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fwrp", "root", "Ar@200703");
                    st = connection.createStatement();
                    String query = "SELECT * FROM Feedback";
                    rs = st.executeQuery(query);
                    while (rs.next()) {
            %>
            <tr>
                <td><%= rs.getInt("feedback_id") %></td>
                <td><%= rs.getString("UserID") %></td>
                <td><%= rs.getDate("feedback_date") %></td>
                <td><%= rs.getString("feedback_text") %></td>
                <td><%= rs.getInt("rating") %></td>
            </tr>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (st != null) st.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            %>
        </table>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>© 2024 Food Waste Reduction Platform</p>
    </div>
</body>
</html>
