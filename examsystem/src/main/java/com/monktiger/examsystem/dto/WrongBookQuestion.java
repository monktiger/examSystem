package com.monktiger.examsystem.dto;

import com.monktiger.examsystem.entity.CopyToQuestion;
import com.monktiger.examsystem.entity.ExamToQuestion;

public class WrongBookQuestion {
    private Integer id;
    private String title;
    private Integer score;
    private Integer getScore;
    private Integer type;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String answer;
    private String correct;
    public WrongBookQuestion(CopyToQuestion ctq, ExamToQuestion etq) {
        this.id = etq.getId();
        this.title = etq.getTitle();
        this.score = etq.getScore();
        this.type = etq.getType();
        this.answerA = etq.getAnswerA();
        this.answerB = etq.getAnswerB();
        this.answerC = etq.getAnswerC();
        this.answerD = etq.getAnswerD();
        this.answer = ctq.getAnswer();
        this.getScore = ctq.getScore();
        this.correct =etq.getCurrent();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGetScore() {
        return getScore;
    }

    public void setGetScore(Integer getScore) {
        this.getScore = getScore;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
