package com.monktiger.examsystem.entity;

import java.io.Serializable;

/**
 * tb_copy_to_quetion
 * @author 唐伟嘉
 */
public class CopyToQuetionKey implements Serializable {
    /**
     * 学生答卷id
     */
    private Integer copyId;

    /**
     * 试卷中的该题目序号
     */
    private Integer id;

    private static final long serialVersionUID = 1L;

    public Integer getCopyId() {
        return copyId;
    }

    public void setCopyId(Integer copyId) {
        this.copyId = copyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        CopyToQuetionKey other = (CopyToQuetionKey) that;
        return (this.getCopyId() == null ? other.getCopyId() == null : this.getCopyId().equals(other.getCopyId()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCopyId() == null) ? 0 : getCopyId().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", copyId=").append(copyId);
        sb.append(", id=").append(id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}