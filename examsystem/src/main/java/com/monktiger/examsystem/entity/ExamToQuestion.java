package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_exam_to_question
 * @author 唐伟嘉
 */
public class ExamToQuestion extends ExamToQuestionKey implements Serializable {
    /**
     * 题目分数
     */
    private Integer score;

    /**
     * 题目详情
     */
    private String title;

    /**
     * 题目类型1 2 3 4 5{单选，多选,填空，判断,主观}
     */
    private Integer type;

    /**
     * 答案
     */
    private String current;

    /**
     * 选择题A选项
     */
    private String answerA;

    /**
     * 选择题B选项
     */
    private String answerB;

    /**
     * 选择题C选项
     */
    private String answerC;

    /**
     * 选择题D选项
     */
    private String answerD;

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
        ExamToQuestion other = (ExamToQuestion) that;
        return (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCurrent() == null ? other.getCurrent() == null : this.getCurrent().equals(other.getCurrent()))
            && (this.getAnswerA() == null ? other.getAnswerA() == null : this.getAnswerA().equals(other.getAnswerA()))
            && (this.getAnswerB() == null ? other.getAnswerB() == null : this.getAnswerB().equals(other.getAnswerB()))
            && (this.getAnswerC() == null ? other.getAnswerC() == null : this.getAnswerC().equals(other.getAnswerC()))
            && (this.getAnswerD() == null ? other.getAnswerD() == null : this.getAnswerD().equals(other.getAnswerD()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCurrent() == null) ? 0 : getCurrent().hashCode());
        result = prime * result + ((getAnswerA() == null) ? 0 : getAnswerA().hashCode());
        result = prime * result + ((getAnswerB() == null) ? 0 : getAnswerB().hashCode());
        result = prime * result + ((getAnswerC() == null) ? 0 : getAnswerC().hashCode());
        result = prime * result + ((getAnswerD() == null) ? 0 : getAnswerD().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", score=").append(score);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", current=").append(current);
        sb.append(", answerA=").append(answerA);
        sb.append(", answerB=").append(answerB);
        sb.append(", answerC=").append(answerC);
        sb.append(", answerD=").append(answerD);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}