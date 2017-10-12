package com.mijack.sbbs.vo;

import java.util.HashMap;
import java.util.Map;

public class Response<T> extends HashMap<String, Object> {

    public static final int CODE_OK = 200;
    public static final int CODE_FAILED = 0;

    public Response<T> put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Response<T> data(T data) {
        put("data", data);
        return this;
    }

    public Response<T> code(int code) {
        put("code", code);
        return this;
    }

    public Response<T> msg(String msg) {
        put("msg", msg);
        return this;
    }

    public static <T> Response<T> ok(T data) {
        return new Response<T>().data(data).code(CODE_OK);
    }


    public static <T> Response<T> failed(String msg) {
        return failed(msg, CODE_FAILED);
    }

    public static <T> Response<T> failed(String msg, int code) {
        return new Response<T>().code(code).msg(msg);
    }
}
