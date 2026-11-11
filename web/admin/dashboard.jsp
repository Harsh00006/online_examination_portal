
<%@page import="com.exam.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User admin = (User) session.getAttribute("user");

    if (admin == null ||
        !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
</head>

<body>

    <h1>Online Examination Portal - Admin Dashboard</h1>

    <h2>Welcome, <%= admin.getName() %>!</h2>

    <h3>Management</h3>

    <ul>
        <li>
            <a href="<%= request.getContextPath() %>/admin/exams">
                Manage Exams
            </a>
        </li>

        <li>
                <a href="<%= request.getContextPath() %>/admin/results">
        View Results
    </a>
        </li>
    </ul>

    <a href="<%= request.getContextPath() %>/logout">
        Logout
    </a>

</body>
</html>
