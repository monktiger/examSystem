package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_copy_to_quetion
 * @author 唐伟嘉
 */
public class CopyToQuetion extends CopyToQuetionKey implements Serializable {
    /**
     * 试题得分
     */
    private Integer score;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 试题是否完成
     */
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
        CopyToQuetion other = (CopyToQuetion) that;
        return (this.getCopyId() == null ? other.getCopyId() == null : this.getCopyId().equals(other.getCopyId()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getAlready() == null ? other.getAlready() == null : this.getAlready().equals(other.getAlready()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCopyId() == null) ? 0 : getCopyId().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getAlready() == null) ? 0 : getAlready().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", score=").append(score);
        sb.append(", answer=").append(answer);
        sb.append(", already=").append(already);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}