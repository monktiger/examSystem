package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_question
 * @author 
 */
public class Question implements Serializable {
    private Integer id;

    private String title;

    private Integer type;

    private String category;

    private String current;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private String openId;

    private static final long serialVersionUID = 1L;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getCurrent() == null ? other.getCurrent() == null : this.getCurrent().equals(other.getCurrent()))
            && (this.getAnswerA() == null ? other.getAnswerA() == null : this.getAnswerA().equals(other.getAnswerA()))
            && (this.getAnswerB() == null ? other.getAnswerB() == null : this.getAnswerB().equals(other.getAnswerB()))
            && (this.getAnswerC() == null ? other.getAnswerC() == null : this.getAnswerC().equals(other.getAnswerC()))
            && (this.getAnswerD() == null ? other.getAnswerD() == null : this.getAnswerD().equals(other.getAnswerD()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getCurrent() == null) ? 0 : getCurrent().hashCode());
        result = prime * result + ((getAnswerA() == null) ? 0 : getAnswerA().hashCode());
        result = prime * result + ((getAnswerB() == null) ? 0 : getAnswerB().hashCode());
        result = prime * result + ((getAnswerC() == null) ? 0 : getAnswerC().hashCode());
        result = prime * result + ((getAnswerD() == null) ? 0 : getAnswerD().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", category=").append(category);
        sb.append(", current=").append(current);
        sb.append(", answerA=").append(answerA);
        sb.append(", answerB=").append(answerB);
        sb.append(", answerC=").append(answerC);
        sb.append(", answerD=").append(answerD);
        sb.append(", openId=").append(openId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}