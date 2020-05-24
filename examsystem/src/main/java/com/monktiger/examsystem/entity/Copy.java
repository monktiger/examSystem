package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_copy
 * @author  唐伟嘉
 */
public class Copy implements Serializable {
    /**
     * 学生答卷id
     */
    private Integer copyId;

    /**
     * 唯一组标识ID
     */
    private String groupId;

    /**
     * 试卷id
     */
    private Integer examId;

    /**
     * 用户id
     */
    private String openId;

    /**
     * 答卷的情况，预备考试0，可以答题1，考试结束已经批改2，考试结束待批改3
     */
    private Integer status;

    /**
     * 教师可以对试卷进行评价
     */
    private String judge;

    /**
     * 答卷成绩
     */
    private Integer score;

    private static final long serialVersionUID = 1L;

    public Integer getCopyId() {
        return copyId;
    }

    public void setCopyId(Integer copyId) {
        this.copyId = copyId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
        Copy other = (Copy) that;
        return (this.getCopyId() == null ? other.getCopyId() == null : this.getCopyId().equals(other.getCopyId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getJudge() == null ? other.getJudge() == null : this.getJudge().equals(other.getJudge()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCopyId() == null) ? 0 : getCopyId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getJudge() == null) ? 0 : getJudge().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", copyId=").append(copyId);
        sb.append(", groupId=").append(groupId);
        sb.append(", examId=").append(examId);
        sb.append(", openId=").append(openId);
        sb.append(", status=").append(status);
        sb.append(", judge=").append(judge);
        sb.append(", score=").append(score);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}