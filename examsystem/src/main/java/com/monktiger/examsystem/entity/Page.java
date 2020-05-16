package com.monktiger.examsystem.entity;

import java.util.List;

//page控制
public class Page<T> {
    private int start;
    private int end;
    private int totalPage;
    private int pageSize;
    private int totalRecord;
    private int pageNum;
    private int startIndex;
    List<T> list;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page(int pageNum, int totalRecord) {
        this.pageNum = pageNum;
        this.pageSize =15;
        this.totalRecord = totalRecord;
        if (totalRecord % pageSize == 0) {
            this.totalPage = totalRecord / pageSize;
        } else {
            this.totalPage = totalRecord / pageSize + 1;
        }
        this.pageNum = pageNum;
        this.startIndex = pageNum * pageSize - pageSize;
        if (totalPage <= 5) {
            this.start = 1;
            this.end = totalPage;
        } else {
            this.start = pageNum - 2;
            this.end = pageNum + 2;
            if (start <= 0) {
                start = 1;
                end = start + 4;
            }
            if (end > totalPage) {
                end = totalPage;
                start = end - 4;
            }
        }
    }
}