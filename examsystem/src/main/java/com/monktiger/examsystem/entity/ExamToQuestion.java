package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_exam_to_question
 * @author 
 */
public class ExamToQuestion implements Serializable {
    private Integer score;

    private String title;

    private Integer type;

    private String current;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private String Id;

    private static final long serialVersionUID = 1L;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
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

    @Override
    public String toString() {
        return "ExamToQuestion{" +
                "score=" + score +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", current='" + current + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }
}