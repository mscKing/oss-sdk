package com.wecarry.oss.common.util;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * 16进制字符串工具
 *
 * @author miaosc
 * @date 10/24/2019
 */
public class HexUtil {

    private final static HexBinaryAdapter BINARY_ADAPTER = new HexBinaryAdapter();

    /**
     * 字节转16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {
        return BINARY_ADAPTER.marshal(bytes).toUpperCase();
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] toBinaryBytes(String hexString) {
        return BINARY_ADAPTER.unmarshal(hexString);
    }
}
