package com.wecarry.oss.common.util;

import com.wecarry.oss.common.constant.CommonConstant;

/**
 * 文件名称处理工具
 *
 * @author miaosc
 * @date 2020/9/13 0013
 */
public class NameUtil {

    /**
     * 特殊字符不能结尾
     */
    public static final String SPECIAL_CHAR = "<>/\\|:\"*?";

    public static final String SPECIAL_CHAR_REG = "[<>/\\|:\"*?]";

    /**
     * 提取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String extractExtName(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i == -1) {
            return CommonConstant.EMPTY_STR;
        } else {
            String substring = fileName.substring(i + 1);
            String result = substring.length() <= 64 ? substring : substring.substring(0, 64);
            return filterSpecialChar(result);
        }
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    public static String filterSpecialChar(String str) {
        return str.replaceAll(SPECIAL_CHAR_REG, "");
    }
}
