package com.monktiger.examsystem.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_exam
 * @author 
 */
public class Exam  implements Serializable {
    private String name;

    private String id;

    private String groupId;

    private Boolean type;

    private String score;

    private Date beginTime;

    private Date endTime;

    private Integer publisherId;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", type=" + type +
                ", score='" + score + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", publisherId=" + publisherId +
                ", status=" + status +
                '}';
    }
}