<%
    Integer score =
            (Integer) request.getAttribute("score");

    Integer correctAnswers =
            (Integer) request.getAttribute("correctAnswers");

    Integer wrongAnswers =
            (Integer) request.getAttribute("wrongAnswers");

    Integer examId =
            (Integer) request.getAttribute("examId");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Exam Result</title>
</head>

<body>

    <h1>Exam Result</h1>

    <hr>

    <h2>Your Score</h2>

    <h1><%= score %></h1>

    <p>
        Correct Answers:
        <%= correctAnswers %>
    </p>

    <p>
        Wrong Answers:
        <%= wrongAnswers %>
    </p>

    <hr>

    <p>
        Exam ID:
        <%= examId %>
    </p>

    <br>

    <a href="<%= request.getContextPath() %>/student/dashboard.jsp">
        Back to Dashboard
    </a>

</body>
</html>