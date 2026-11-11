<%@page import="java.util.List"%>
<%@page import="com.exam.model.Result"%>
<%@page import="com.exam.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User admin = (User) session.getAttribute("user");

    if (admin == null ||
        !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Result> results =
            (List<Result>) request.getAttribute("results");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Exam Results</title>
</head>

<body>

    <h1>All Exam Results</h1>

    <table border="1" cellpadding="8">

        <thead>
            <tr>
                <th>Result ID</th>
                <th>Student Name</th>
                <th>Email</th>
                <th>Exam</th>
                <th>Score</th>
                <th>Correct</th>
                <th>Wrong</th>
                <th>Submitted At</th>
            </tr>
        </thead>

        <tbody>

            <% if (results != null && !results.isEmpty()) {
                for (Result result : results) { %>

                <tr>
                    <td><%= result.getResultId() %></td>
                    <td><%= result.getStudentName() %></td>
                    <td><%= result.getStudentEmail() %></td>
                    <td><%= result.getExamTitle() %></td>
                    <td><%= result.getScore() %></td>
                    <td><%= result.getCorrectAnswers() %></td>
                    <td><%= result.getWrongAnswers() %></td>
                    <td><%= result.getSubmittedAt() %></td>
                </tr>

            <%  }
               } else { %>

                <tr>
                    <td colspan="8">No exam results available.</td>
                </tr>

            <% } %>

        </tbody>

    </table>

    <br>

    <a href="<%= request.getContextPath() %>/admin/dashboard.jsp">
        Back to Dashboard
    </a>

</body>
</html>