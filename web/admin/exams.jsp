<!DOCTYPE html>
<html>
<head>
    <title>Add Question</title>
</head>

<body>

    <h2>Add Question</h2>

    <form action="<%= request.getContextPath() %>/add-question"
          method="post">

        <label>Exam ID:</label>
        <input type="number" name="examId" required>

        <br><br>

        <label>Question:</label>
        <br>
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

    <br>

    <a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>