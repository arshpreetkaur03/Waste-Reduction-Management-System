<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Inventory</title>
<style>
/* CSS Styles */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    /*background-color: #f9f9f9; /* Light gray background */
    background-image: url('./css/food.jpeg');
}

.header {
    background-color: #293d3d; /* Dark gray header background */
    color: #fff;
    padding: 20px;
    text-align: center;
    font-size: 24px;
}

.container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background-color: #fff; /* White container background */
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px; /* Add margin at the bottom */
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

th, td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #ddd; /* Gray border bottom */
}

th {
    background-color: #527a7a; /* Dark gray header background */
    color: #fff;
    text-transform: uppercase;
}

.footer {
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100%;
    background-color: #293d3d;
    color: white;
    text-align: center;
    padding: 20px;
}


tr:nth-child(even) {
    background-color: #f2f2f2; /* Light gray alternating row background */
}
tr:nth-child(odd) {
    background-color: #fff; /* Adjust the opacity and color as needed */
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
    <div class="header">
        <h1>Surplus Items</h1>
        <div class="header-buttons">
            <a href="LoginServlet" class="button">Logout</a>
            
</div>
    </div>
    
   
        <table border="1">
            <tr>
                <th>Item ID</th>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Expiration Date</th>
                <th>Retailer Price</th>
                <th>Is Donation</th>
                <th>Is Sale</th>
                <th>Discount Price</th>
            </tr>
            <%
            Connection connection = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fwrp", "root", "Ar@200703");
                st = connection.createStatement();
                String query = "SELECT * FROM Inventory WHERE IsDonation = 1 OR IsSale = 1";
                rs = st.executeQuery(query);
                while (rs.next()) {
            %>
            <tr>
                <td><%=rs.getString(1)%></td>
                <td><%=rs.getString(2)%></td>
                <td><%=rs.getString(3)%></td>
                <td><%=rs.getString(4)%></td>
                <td><%=rs.getString(5)%></td>
                <td><%=rs.getString(6)%></td>
                <td><%=rs.getString(7)%></td>
                <td><%=rs.getString(8)%></td>
            </tr>
            <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            %>
        </table>
    </div>
    <div class="footer">
        <p>© 2024 Food Waste Reduction Platform</p>
    </div>
</body>
</html>