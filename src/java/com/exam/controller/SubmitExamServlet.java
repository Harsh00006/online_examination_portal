package com.exam.controller;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.dao.ResultDAO;
import com.exam.model.User;
import javax.servlet.http.HttpSession;

@WebServlet("/submit-exam")
public class SubmitExamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("========== SUBMIT EXAM SERVLET CALLED ==========");
        // Get exam ID
        int examId = Integer.parseInt(
                request.getParameter("examId")
        );

        // Get all questions of this exam
        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByExamId(examId);

        int score = 0;
        int correctAnswers = 0;
        int wrongAnswers = 0;

        // Check every question
        for (Question question : questions) {

            String selectedOption =
                    request.getParameter(
                            "question_" + question.getQuestionId()
                    );

            String correctOption =
                    question.getCorrectOption();

            if (selectedOption != null) {

                if (selectedOption.equals(correctOption)) {

                    score += question.getMarks();
                    correctAnswers++;

                } else {

                    wrongAnswers++;
                }
            }
        }
        
        HttpSession session = request.getSession(false);

User user = (User) session.getAttribute("user");

int userId = user.getUserId();

ResultDAO resultDAO = new ResultDAO();

int resultId = resultDAO.saveResult(
        userId,
        examId,
        score,
        correctAnswers,
        wrongAnswers
);

System.out.println("Generated Result ID: " + resultId);

for (Question question : questions) {

    String selectedOption =
            request.getParameter(
                    "question_" + question.getQuestionId()
            );

    String correctOption =
            question.getCorrectOption();

    boolean isCorrect = false;

    if (selectedOption != null) {
        isCorrect = selectedOption.equals(correctOption);
    }
    
    System.out.println(
        "Question ID: " + question.getQuestionId()
        + ", Selected: " + selectedOption
        + ", Correct: " + correctOption
        + ", Is Correct: " + isCorrect
);

    resultDAO.saveStudentAnswer(
            resultId,
            question.getQuestionId(),
            selectedOption,
            isCorrect
    );
}

        // Send result to result.jsp
        request.setAttribute("score", score);
        request.setAttribute(
                "correctAnswers",
                correctAnswers
        );
        request.setAttribute(
                "wrongAnswers",
                wrongAnswers
        );

        request.setAttribute("examId", examId);

        request.getRequestDispatcher(
                "student/result.jsp"
        ).forward(request, response);
    }
}