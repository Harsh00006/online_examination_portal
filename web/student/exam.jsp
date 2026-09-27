<%@ page import="java.util.List" %>
<%@ page import="com.exam.model.Question" %>

<%
    List<Question> questions =
            (List<Question>) request.getAttribute("questions");

    Integer examId =
            (Integer) request.getAttribute("examId");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Online Exam</title>
</head>

<body>

    <h1>Online Exam</h1>

    <%
        if (questions != null && !questions.isEmpty()) {
    %>

        <form action="<%= request.getContextPath() %>/submit-exam"
              method="post">

            <!-- Exam ID -->
            <input type="hidden"
                   name="examId"
                   value="<%= examId %>">

            <%
                int questionNumber = 1;

                for (Question question : questions) {
            %>

                <div>

                    <h3>
                        Q<%= questionNumber %>.
                        <%= question.getQuestionText() %>
                    </h3>

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="A">
                    <%= question.getOptionA() %>

                    <br>

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="B">
                    <%= question.getOptionB() %>

                    <br>

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="C">
                    <%= question.getOptionC() %>

                    <br>

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="D">
                    <%= question.getOptionD() %>

                    <hr>

                </div>

            <%
                    questionNumber++;
                }
            %>

            <button type="submit">Submit Exam</button>

        </form>

    <%
        } else {
    %>

        <p>No questions available for this exam.</p>

    <%
        }
    %>

    <br>

    <a href="<%= request.getContextPath() %>/student/dashboard.jsp">
        Back to Dashboard
    </a>

</body>
</html>