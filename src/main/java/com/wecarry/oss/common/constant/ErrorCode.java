package com.wecarry.oss.common.constant;

import lombok.Getter;

/**
 * 通用错误码枚举
 *
 * @author miaosc
 * @date 10/22/2019
 */
@Getter
public enum ErrorCode {

    /**
     * 无效的参数
     */
    ILLEGAL_ARGUMENT(1_002,"无效的参数"),

    /**
     * 要操作的资源不存在
     */
    NOT_EXIST(1_001,"该资源不存在"),

    /**
     * 服务器异常
     */
    INTERNAL_ERROR(1_000,"系统异常"),

    /**
     * 失败
     */
    FAILED(0,"操作失败"),

    /**
     * 成功
     */
    SUCCESS(1,"操作成功");


    /**
     * 错误码
     */
    private int code;

    /**
     * 错误码用途描述
     */
    private String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
