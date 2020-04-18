package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_group
 * @author 
 */
public class Group implements Serializable {
    private String groupId;

    private String openId;

    private String name;

    private Boolean status;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId='" + groupId + '\'' +
                ", openId='" + openId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}