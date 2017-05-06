package com.abel.hwes.model;

import java.util.List;

/**
 * @author Abel.Li
 * @param <T>
 */
public class PageBean<T> {
    private int currentPage;// 当前页数
    private int pageCount;// 一共的页数
    private int totalCount;// 数据条数
    private int pageSize = 10;// 每页的数据条数
    private int start;// 起始数据位置
    private int end;// 结束
    private List<T> list = null;

    public void init() {
        /*
         * 根totalCount 和pageSize计算页数pageCount
         */
        int pageCountX = totalCount / pageSize;
        if (totalCount >= pageSize) {
            this.pageCount = totalCount % pageSize == 0 ? pageCountX : pageCountX + 1;
        } else {
            this.pageCount = 1;
        }
        // 判断页数和当前页数
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        // 根据当前页计算起始和结束条目
        this.start = (currentPage - 1) * pageSize;
        this.end = currentPage * pageSize;
    }

    public PageBean(int currentPage, int totalCount, int pageSize) {
        super();
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        init();
    }

    public PageBean(int currentPage, int totalCount, int pageSize, List<T> list) {
        super();
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.list = list;
        init();
    }

    public PageBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "PageBean [totalCount=" + totalCount + ", end=" + end + ", list=" + list + ", pageSize=" + pageSize
                + ", currentPage=" + currentPage + ", pageCount=" + pageCount + ", start=" + start + "]";
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
