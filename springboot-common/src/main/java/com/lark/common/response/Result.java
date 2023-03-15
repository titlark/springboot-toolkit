package com.lark.common.response;

import com.lark.common.code.ErrorCode;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 响应结果实体类
 *
 * @param <T>
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -6786174899357122136L;

    private int code;
    private String message;
    private Object data;
    private List<T> list;
    private long count;
    private String token;
    /**
     * 设置需要跳转的页面地址
     */
    private String redirect;

    private Result() {
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return ok(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> ok(List<T> list, long count) {
        return ok(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null, list, count);
    }

    public static <T> Result<T> ok(Integer code, String message, T data) {
        return ok(code, message, data, Collections.emptyList(), 0);
    }

    public static <T> Result<T> ok(Integer code, String message, T data, List<T> list, long count) {
        return ok(code, message, data, list, count, null, null);
    }

    public static <T> Result<T> ok(Integer code, String message, T data, List<T> list, long count, String token, String redirect) {
        Result result = new Result();
        result.code(code);
        result.message(message);
        result.data(data);
        result.list(list);
        result.count(count);
        result.token(token);
        result.redirect(redirect);
        return result;
    }

    public static <T> Result<T> error() {
        return error(ErrorCode.ERROR.getCode(), ErrorCode.ERROR.getMessage());
    }

    public static <T> Result<T> error(String message) {
        return error(ErrorCode.ERROR.getCode(), message);
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result result = new Result();
        result.code(code);
        result.message(message);
        return result;
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(Object data) {
        this.data = data;
        return this;
    }

    public Result<T> list(List<T> list) {
        this.list = list;
        return this;
    }

    public Result<T> count(long count) {
        this.count = count;
        return this;
    }

    public Result<T> token(String token) {
        this.token = token;
        return this;
    }

    public Result<T> redirect(String redirect) {
        this.redirect = redirect;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public List<T> getList() {
        return list;
    }

    public long getCount() {
        return count;
    }

    public String getToken() {
        return token;
    }

    public String getRedirect() {
        return redirect;
    }

}
