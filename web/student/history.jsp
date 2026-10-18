
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.exam.model.Result"%>

<%
    User loggedInUser = (User) session.getAttribute("user");

    if (loggedInUser == null) {
        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );
        return;
    }

    List<Result> results =
        (List<Result>) request.getAttribute("results");

    SimpleDateFormat dateFormat =
        new SimpleDateFormat("dd-MM-yyyy hh:mm a");
%>

<%@page import="com.exam.model.User"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam History</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 1000px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px #ddd;
        }

        h2 {
            color: #263238;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background: #263b80;
            color: white;
        }

        tr:nth-child(even) {
            background: #f2f4f8;
        }

        .back-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 16px;
            background: #263b80;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .empty {
            text-align: center;
            padding: 20px;
            color: #666;
        }
    </style>
</head>

<body>
<div class="container">

    <h2>My Exam History</h2>

    <p>
        Welcome, <strong><%= loggedInUser.getName() %></strong>
    </p>

    <% if (results == null || results.isEmpty()) { %>

        <div class="empty">
            <p>No exam history found.</p>
            <p>Attempt an exam to see your results here.</p>
        </div>

    <% } else { %>

        <table>
            <thead>
                <tr>
                    <th>Exam Name</th>
                    <th>Score</th>
                    <th>Correct Answers</th>
                    <th>Wrong Answers</th>
                    <th>Submitted At</th>
                </tr>
            </thead>

            <tbody>
            <% for (Result result : results) { %>
                <tr>
                    <td><%= result.getExamTitle() %></td>
                    <td><%= result.getScore() %></td>
                    <td><%= result.getCorrectAnswers() %></td>
                    <td><%= result.getWrongAnswers() %></td>
                   
                    <td>
                        <%= result.getSubmittedAt() == null
                            || result.getSubmittedAt().trim().isEmpty()
                            ? "-"
                            : result.getSubmittedAt() %>
                    </td>

                </tr>
            <% } %>
            </tbody>
        </table>

    <% } %>

    <a class="back-btn"
       href="<%= request.getContextPath() %>/student/dashboard.jsp">
        Back to Dashboard
    </a>

</div>
</body>
</html>
