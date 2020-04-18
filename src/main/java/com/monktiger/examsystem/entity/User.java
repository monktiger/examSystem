package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_user
 * @author 
 */
public class User implements Serializable {
    private String openId;

    private String nickname;

    private String name;

    private String avatarurl;

    private Boolean availble;

    private static final long serialVersionUID = 1L;

    public User(String openId,String nickname,String name,String avatarurl, boolean availble){
        this.openId = openId;
        this.nickname = nickname;
        this.name = name;
        this.avatarurl = avatarurl;
        this.availble = availble;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public Boolean getAvailble() {
        return availble;
    }

    public void setAvailble(Boolean availble) {
        this.availble = availble;
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
        User other = (User) that;
        return (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAvatarurl() == null ? other.getAvatarurl() == null : this.getAvatarurl().equals(other.getAvatarurl()))
            && (this.getAvailble() == null ? other.getAvailble() == null : this.getAvailble().equals(other.getAvailble()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAvatarurl() == null) ? 0 : getAvatarurl().hashCode());
        result = prime * result + ((getAvailble() == null) ? 0 : getAvailble().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", openId=").append(openId);
        sb.append(", nickname=").append(nickname);
        sb.append(", name=").append(name);
        sb.append(", avatarurl=").append(avatarurl);
        sb.append(", availble=").append(availble);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}