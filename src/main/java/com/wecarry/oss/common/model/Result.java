package com.wecarry.oss.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * http响应结果
 *
 * @author miaosc
 * @date 10/22/2019
 */
@Setter
@Getter
public class Result<T> {

    /**
     * 操作成功消息描述
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 操作失败消息描述
     */
    public static final String FAILED_MESSAGE = "操作失败";

    /**
     * 操作成功code
     */
    public static final int SUCCESS_CODE = 0;

    /**
     * 操作失败code
     */
    public static final int FAILED_CODE = 1;

    /**
     * 响应的消息描述
     */
    private String message;

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应的数据内容,可以为null
     */
    private T data;

    private Result(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static Result<?> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(SUCCESS_MESSAGE, SUCCESS_CODE, data);
    }

    public static Result<?> fail() {
        return fail(FAILED_CODE, FAILED_MESSAGE);
    }

    public static Result<?> fail(int code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(int code, String message, T data) {
        return new Result<>(message, code, data);
    }

}
