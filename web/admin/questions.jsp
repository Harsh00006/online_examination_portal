
<%@page import="java.util.List"%>
<%@page import="com.exam.model.Exam"%>
<%@page import="com.exam.model.Question"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Exam selectedExam =
        (Exam) request.getAttribute("selectedExam");

    List<Question> questions =
        (List<Question>) request.getAttribute("questions");

    if (selectedExam == null) {
        response.sendRedirect(
            request.getContextPath() + "/admin/exams"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Questions</title>
</head>
<body>

    <h1>Manage Questions</h1>

    <h2>Exam Details</h2>

    <p>Exam ID: <%= selectedExam.getExamId() %></p>
    <p>Exam Title: <%= selectedExam.getTitle() %></p>
    <p>Total Marks: <%= selectedExam.getTotalMarks() %></p>

    <hr>

    <h2>Add Question</h2>

    <form action="<%= request.getContextPath() %>/add-question"
          method="post">

        <input type="hidden"
               name="examId"
               value="<%= selectedExam.getExamId() %>">

        <label>Question:</label><br>
        <textarea name="questionText"
                  rows="4"
                  cols="50"
                  required></textarea>

        <br><br>

        <label>Option A:</label>
        <input type="text" name="optionA" required>

        <br><br>

        <label>Option B:</label>
        <input type="text" name="optionB" required>

        <br><br>

        <label>Option C:</label>
        <input type="text" name="optionC" required>

        <br><br>

        <label>Option D:</label>
        <input type="text" name="optionD" required>

        <br><br>

        <label>Correct Option:</label>
        <select name="correctOption" required>
            <option value="">-- Select Correct Option --</option>
            <option value="A">A</option>
            <option value="B">B</option>
            <option value="C">C</option>
            <option value="D">D</option>
        </select>

        <br><br>

        <label>Marks:</label>
        <input type="number"
               name="marks"
               value="1"
               min="1"
               required>

        <br><br>

        <button type="submit">Add Question</button>

    </form>

    <hr>

    <h2>Existing Questions</h2>

    <%
        if (questions != null && !questions.isEmpty()) {
    %>

    <table border="1" cellpadding="8">
        <tr>
            <th>Question ID</th>
            <th>Question</th>
            <th>Option A</th>
            <th>Option B</th>
            <th>Option C</th>
            <th>Option D</th>
            <th>Correct Option</th>
            <th>Marks</th>
        </tr>

        <%
            for (Question question : questions) {
        %>

        <tr>
            <td><%= question.getQuestionId() %></td>
            <td><%= question.getQuestionText() %></td>
            <td><%= question.getOptionA() %></td>
            <td><%= question.getOptionB() %></td>
            <td><%= question.getOptionC() %></td>
            <td><%= question.getOptionD() %></td>
            <td><%= question.getCorrectOption() %></td>
            <td><%= question.getMarks() %></td>
        </tr>

        <%
            }
        %>
    </table>

    <%
        } else {
    %>

    <p>No questions added for this exam yet.</p>

    <%
        }
    %>

    <br>

    <a href="<%= request.getContextPath() %>/admin/exams">
        Back to Manage Exams
    </a>

</body>
</html>
