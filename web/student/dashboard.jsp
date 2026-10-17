<%@ page import="com.exam.model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
</head>

<body>

    <h1>Student Dashboard</h1>

    <h2>Welcome, <%= user.getName() %>!</h2>

    <p>You have successfully logged in.</p>

    <hr>

    <h3>Student Menu</h3>

    <a href="../exams">Available Exams</a>
    <br><br>

    <a href="#">My Results</a>
    <br><br>

    <a href="<%= request.getContextPath() %>/history">
    Exam History
</a>
    
    <a href="http://localhost:8080/OnlineExamPortal/login.jsp">Logout</a>

</body>
</html>