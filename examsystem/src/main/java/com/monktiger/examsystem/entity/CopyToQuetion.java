package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_copy_to_quetion
 * @author 
 */
public class CopyToQuetion implements Serializable {
    private String copyId;

    private String id;

    private Integer score;

    private String answer;

    private Boolean already;

    private static final long serialVersionUID = 1L;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getAlready() {
        return already;
    }

    public void setAlready(Boolean already) {
        this.already = already;
    }


    @Override
    public String toString() {
        return "CopyToQuetion{" +
                "copyId='" + copyId + '\'' +
                ", id='" + id + '\'' +
                ", score=" + score +
                ", answer='" + answer + '\'' +
                ", already=" + already +
                '}';
    }
}