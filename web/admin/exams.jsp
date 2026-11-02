
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Exams</title>
</head>
<body>

    <h1>Manage Exams</h1>

    <h2>Create Exam</h2>

    <form action="<%= request.getContextPath() %>/exam" method="post">

        <label>Exam Title:</label>
        <input type="text" name="title" required>
        <br><br>

        <label>Description:</label>
        <textarea name="description" rows="4" required></textarea>
        <br><br>

        <label>Duration (minutes):</label>
        <input type="number" name="duration" min="1" required>
        <br><br>

        <label>Total Marks:</label>
        <input type="number" name="totalMarks" min="1" required>
        <br><br>

        <button type="submit">Create Exam</button>

    </form>

    <hr>

   
<%@page import="java.util.List"%>
<%@page import="com.exam.model.Exam"%>

<h2>Existing Exams</h2>

<%
    List<Exam> exams =
        (List<Exam>) request.getAttribute("exams");

    if (exams != null && !exams.isEmpty()) {
%>

<table border="1" cellpadding="8">
    <tr>
        <th>Exam ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Duration (Minutes)</th>
        <th>Total Marks</th>
        <th>Actions</th>
    </tr>

    <%
        for (Exam exam : exams) {
    %>

    <tr>
        <td><%= exam.getExamId() %></td>
        <td><%= exam.getTitle() %></td>
        <td><%= exam.getDescription() %></td>
        <td><%= exam.getDurationMinutes() %></td>
        <td><%= exam.getTotalMarks() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/admin/manage-questions?examId=<%= exam.getExamId() %>">
                Manage Questions
            </a>
        </td>
    </tr>

    <%
        }
    %>
</table>

<%
    } else {
%>

<p>No exams found.</p>

<%
    }
%>

    <p>
        <a href="<%= request.getContextPath() %>/admin/dashboard.jsp">
            Back to Dashboard
        </a>
    </p>

</body>
</html>
