<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Subscription Form</title>
    <link rel="stylesheet" href="./css/subscription.css">
    <style>
       body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-image: url('./css/food.jpeg');
    display: flex;
    flex-direction: column; /* Ensures vertical layout */
    align-items: center; /* Centers only the content that needs to be centered */
    min-height: 100vh; /* Full viewport height */
}

.header, .footer {
    width: 100%;
    background-color: #293d3d;
    color: #fff;
    padding: 10px 0; /* Top and bottom padding */
    text-align: center;
    font-size: 24px;
}
.footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    background-color: #293d3d;
    color: #fff;
    padding: 10px 0; /* Top and bottom padding */
    text-align: center;
    font-size: 24px;
}

.header-buttons {
    position: absolute;
    right: 20px;
}

form {
    max-width: 400px;
    padding: 20px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin: 20px 0; /* Vertical margin for separation */
    text-align: center;
}
input[type="text"],
input[type="email"],
input[type="number"],
input[type="date"],
select {
    width: calc(100% - 22px);
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
button[type="submit"] {
    width: calc(100% - 22px);
    padding: 10px;
    background-color: #293d3d;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
button[type="submit"]:hover {
    background-color: #1d2929;
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
        <h1>User Subscription Form</h1>
        <div class="header-buttons">
            <a href="login.jsp" class="button">Logout</a>
        </div>
    </div>

    <form action="SubscriptionServlet" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>
        
        <label for="communicationMethod">Preferred Communication Method:</label>
        <select id="communicationMethod" name="communicationMethod">
            <option value="email">Email</option>
            <option value="phone">Phone</option>
        </select><br>
        
        <label for="location">Location:</label>
        <input type="text" id="location" name="location"><br>
        
        <label for="foodPreferences">Food Preferences:</label>
        <input type="text" id="foodPreferences" name="foodPreferences"><br>
        
                <input type="submit" value="Subscribe">
    </form>

    <div class="footer">
        <p>© 2024 Food Waste Reduction Platform</p>
    </div>
</body>
</html>
