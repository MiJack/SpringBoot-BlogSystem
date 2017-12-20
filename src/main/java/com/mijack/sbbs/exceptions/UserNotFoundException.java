package com.mijack.sbbs.exceptions;

/**
 * @author Mr.Yuan
 */
public class UserNotFoundException extends RuntimeException {
    private String msg;

    public UserNotFoundException(String msg) {
        this.msg = msg;
    }
}
