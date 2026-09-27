<%@ page import="java.util.List" %>
<%@ page import="com.exam.model.Exam" %>

<%
    List<Exam> exams = (List<Exam>) request.getAttribute("exams");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Available Exams</title>
</head>

<body>

    <h1>Available Exams</h1>

    <%
        if (exams != null && !exams.isEmpty()) {

            for (Exam exam : exams) {
    %>

        <div>
            <h2><%= exam.getTitle() %></h2>

            <p>
                <%= exam.getDescription() %>
            </p>

            <p>
                Duration:
                <%= exam.getDurationMinutes() %> minutes
            </p>

            <p>
                Total Marks:
                <%= exam.getTotalMarks() %>
            </p>

            <a href="<%= request.getContextPath() %>/start-exam?examId=<%= exam.getExamId() %>">Start Exam</a>

            <hr>
        </div>

    <%
            }

        } else {
    %>

        <p>No exams available.</p>

    <%
        }
    %>

    <br>

    <a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>