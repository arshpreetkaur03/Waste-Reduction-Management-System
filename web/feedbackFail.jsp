<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>
<head>
    <title>Feedback Failure</title> 
    <style>
        /* CSS Styles */
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
        .header {
            background-color: #293d3d; /* Dark gray header background */
            color: #fff;
            padding: 20px;
            text-align: center;
            font-size: 24px;
            width: 100%; /* Make header full width */
        }

        .container {
            max-width: 400px;
    padding: 20px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin: 20px 0; /* Vertical margin for separation */
    text-align: center;
        }

        /* Add your additional styles below */
        p {
            margin-bottom: 20px; /* Added margin-bottom for spacing */
            text-align: center; /* Center-align the text */
        }

        form {
            margin-top: 20px; /* Added margin-top for spacing */
            text-align: center; /* Center-align the form */
        }
       
    </style>
</head>
<body>
    <div class="header">
        <h3>Feedback Submission Failed</h3>
    </div>
    <div class="container">
        <p>Either the user ID or item ID provided is incorrect. Please try again.</p>
        <p>Go back to Feedback Form?</p>
        <form action="FeedbackServlet" method="get" >
            <input type="submit" value="Return to Feedback Form">
        </form>
    </div>
</body>
</html>