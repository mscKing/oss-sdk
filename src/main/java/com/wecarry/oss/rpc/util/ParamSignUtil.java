package com.wecarry.oss.rpc.util;

/**
 * 参数签名工具
 * @author miaosc
 * @date 2020/10/8 0008
 */
public class ParamSignUtil {

    /**
     * 构建待签名的参数字符串
     * <p>
     *     入参顺序按照字典排序
     * </p>
     * @param objects
     * @return
     */
    public static String buildParamString(Object... objects) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (Object object : objects) {
            //忽略掉null
            if (object != null) {
                stringBuilder.append(object.toString());
            }
        }
        return stringBuilder.toString();
    }
}
