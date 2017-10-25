package com.mijack.sbbs.exceptions;

/**
 * @author Mr.Yuan
 * @since 2017/10/24
 */
public class BlogNotFoundException extends RuntimeException {
    private String msg;

    public BlogNotFoundException(String msg) {
        this.msg = msg;
    }
}
