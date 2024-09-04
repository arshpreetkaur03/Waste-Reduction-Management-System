<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Page</title>
    <link rel="stylesheet" href="./css/styles02.css">
</head>
<body>

<div class="register-container">
    <h2>Register</h2>
    
    <form id="registrationForm" action="RegistrationServlet" method="post">
        <input type="text" name="name" placeholder="Name" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" id="password" placeholder="Password" required>
        <p>Password must be at least 8 characters and meet the following requirements:</p>
        <ul>
            <li>At least one uppercase letter</li>
            <li>At least one lowercase letter</li>
            <li>At least one digit</li>
            <li>At least one special character (@$!%*?&)</li>
        </ul>
        <select name="userType" required>
            <option value="" disabled selected>Select User Type</option>
            <option value="Retailer">Retailer</option>
            <option value="Consumer">Consumer</option>
            <option value="Charitable Organization">Charitable Organization</option>
        </select>

        <!-- Removed isSubscribe and notification related fields -->
        <input type="submit" value="Register">
    </form>
</div>

<script>
    const registrationForm = document.getElementById('registrationForm');
    const passwordInput = document.getElementById('password');
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    registrationForm.addEventListener('submit', function(event) {
        if (!passwordPattern.test(passwordInput.value)) {
            event.preventDefault(); // Prevent form submission
            alert('Password must be at least 8 characters and meet the specified requirements.');
        }
    });
</script>

</body>
</html>
