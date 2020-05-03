package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_group
 * @author  tangwj
 */
public class Group implements Serializable {
    private String groupId;

    private String openId;

    private String name;

    private int status;

    private static final long serialVersionUID = 1L;

    public Group(){ }

    public Group(String name){ this.name = name; }

    public Group(String groupId,String openId){this.groupId=groupId;this.openId=openId;}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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