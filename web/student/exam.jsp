<%@ page import="java.util.List" %>
<%@ page import="com.exam.model.Question" %>


<%
    List<Question> questions =
            (List<Question>) request.getAttribute("questions");

    Integer examId =
            (Integer) request.getAttribute("examId");
Integer remainingSecondsObj =
        (Integer) request.getAttribute("remainingSeconds");

int remainingSeconds = remainingSecondsObj != null
        ? remainingSecondsObj : 0;
%>

<!DOCTYPE html>
<html>
<head>
    <title>Online Exam</title>
</head>

<body>

    <h1>Online Exam</h1>
    <h2>
    Time Remaining:
    <span id="timer"></span>
    </h2>

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

     <script>
    let remainingSeconds = <%= remainingSeconds %>;

    const timerElement = document.getElementById("timer");
    const examForm = document.querySelector("form");

    let timerInterval;

    function updateTimer() {
        const minutes = Math.floor(remainingSeconds / 60);
        const seconds = remainingSeconds % 60;

        timerElement.textContent =
            String(minutes).padStart(2, "0") + ":" +
            String(seconds).padStart(2, "0");

        if (remainingSeconds <= 0) {
            clearInterval(timerInterval);
            alert("Exam time is over! Submitting automatically.");

            if (examForm) {
                examForm.submit();
            }
            return;
        }

        remainingSeconds--;
    }

    updateTimer();

    timerInterval = setInterval(updateTimer, 1000);
</script>
</body>
</html>