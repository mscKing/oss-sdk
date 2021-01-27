package com.wecarry.oss.rpc.constant;

/**
 * @author miaosc
 * @date 2020/10/2 0002
 */
public class OssParamConstant {

    /**
     * 身份认证
     */
    public static final String AUTH = "Authorization";

    /**
     * 时间戳
     */
    public static final String TIMESTAMP = "oss-timestamp";

    /**
     * 随机字符串
     */
    public static final String RANDOM = "oss-random";

    /**
     * 认证头部的值前缀
     */
    public static final String AUTH_VALUE_PREFIX="OSS ";

    /**
     * URI参数请求format
     */
    public static final String OSS_URI_FORMAT="/oss/download/%s/%s";
}
