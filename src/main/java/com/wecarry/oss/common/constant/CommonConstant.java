package com.wecarry.oss.common.constant;

/**
 * 通用常量池
 *
 * @author miaosc
 * @date 10/24/2019
 */
public abstract class CommonConstant {

    /**
     * 空字符串
     */
    public static final String EMPTY_STR="";

    /**
     * 同一个凭证调用的时效间隔,10 min
     */
    public static final int SIGN_VALID_PERIOD = 600_000;

    private CommonConstant(){}
}
