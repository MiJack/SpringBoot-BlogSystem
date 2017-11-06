package com.mijack.sbbs.component;

/**
 * 分页的组件对象
 */
public class Pagination {
    private String baseUrl;
    private int firstPage;
    private int endPage;
    private int currentPage;
    private boolean first;
    private boolean last;

    public Pagination(String baseUrl, int firstPage, int endPage, int currentPage, boolean first, boolean last) {
        this.baseUrl = baseUrl;
        this.firstPage = firstPage;
        this.endPage = endPage;
        this.currentPage = currentPage;
        this.first = first;
        this.last = last;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public String getPageUrl(int i) {
        return baseUrl.replace("{pageIndex}", String.valueOf(i));
    }
}
