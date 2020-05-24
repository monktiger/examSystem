package com.monktiger.examsystem.dto;

public class Score implements Comparable<Score>{
    private int copyId;
    private int score;
    private String studentName;
    private int status;

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int compareTo(Score o) {
        return o.getScore()-this.getScore();
    }
}
