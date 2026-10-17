package com.exam.model;

public class Result {

    private int resultId;
    private int userId;
    private int examId;
    private String examTitle;
    private int score;
    private int correctAnswers;
    private int wrongAnswers;
    private String submittedAt;

    public Result() {
    }

    public Result(int resultId,
                  int userId,
                  int examId,
                  String examTitle,
                  int score,
                  int correctAnswers,
                  int wrongAnswers,
                  String submittedAt) {

        this.resultId = resultId;
        this.userId = userId;
        this.examId = examId;
        this.examTitle = examTitle;
        this.score = score;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.submittedAt = submittedAt;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }
}