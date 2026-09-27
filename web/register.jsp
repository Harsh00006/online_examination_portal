<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>
</head>

<body>

    <h2>Student Registration</h2>

    <form action="register" method="post">

        <label>Name:</label>
        <input type="text" name="name" required>
        <br><br>

        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">Register</button>

    </form>

    <br>

    <a href="login.jsp">Already have an account? Login</a>

</body>
</html>