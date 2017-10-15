package com.mijack.sbbs.controller.api;

import java.util.HashMap;
import java.util.List;

/**
 * @author Mr.Yuan
 * @since 2017/10/14
 */
public class PageResponse<T> extends HashMap<String, Object> {

    public static final int CODE_OK = 200;
    public static final int CODE_FAILED = 0;


    public static <T> PageResponse<T> ok(List<T> data) {
        return new PageResponse<T>().data(data).code(CODE_OK);
    }


    public static <T> PageResponse<T> failed(String msg) {
        return failed(msg, CODE_FAILED);
    }

    public static <T> PageResponse<T> failed(String msg, int code) {
        return new PageResponse<T>().code(code).msg(msg);
    }

    public PageResponse<T> data(List<T> data) {
        put("data", data);
        return this;
    }

    public PageResponse<T> currentPage(int currentPage) {
        put("currentPage", currentPage);
        return this;
    }

    public PageResponse<T> isFirstPage(boolean isFirst) {
        put("isFirst", isFirst);
        return this;
    }

    public PageResponse<T> isLastPage(boolean isLast) {
        put("isLast", isLast);
        return this;
    }

    public PageResponse<T> firstPage(int firstPage) {
        put("firstPage", firstPage);
        return this;
    }

    public PageResponse<T> lastPage(int endPage) {
        put("endPage", endPage);
        return this;
    }

    public PageResponse<T> msg(String msg) {
        put("msg", msg);
        return this;
    }

    public PageResponse<T> code(int code) {
        put("code", code);
        return this;
    }
}
