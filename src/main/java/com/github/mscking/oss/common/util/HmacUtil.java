package com.github.mscking.oss.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Hmac加密工具
 *
 * @author miaosc
 * @date 2019/12/28 0028
 */
public final class HmacUtil {

    public static final String HMAC_MD5 = "HMACMD5";

    public static final String HMAC_SHA1 = "HMACSHA1";

    public static final String HMAC_SHA256 = "HMACSHA256";

    public static final String HMAC_SHA512 = "HMACSHA512";

    /**
     * 加密mac
     */
    private final Mac mac;

    private HmacUtil(String alg, byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeyException {
        // 生成密钥
        SecretKeySpec sk = new SecretKeySpec(keyBytes, alg);
        // HmacMD5算法
        this.mac = Mac.getInstance(alg);
        mac.init(sk);
    }

    /**
     * 构建hmac工具
     *
     * @param alg 算法
     * @param keyBytes 密钥字节
     * @return 加密工具
     */
    public static HmacUtil buildHmac(String alg, byte[] keyBytes) {
        try {
            return new HmacUtil(alg, keyBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建hmac工具
     *
     * @param key 密钥
     * @return 加密工具
     */
    public static HmacUtil buildHmacMD5(String key) {
        try {
            return new HmacUtil(HMAC_MD5, key.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建hmac工具
     *
     * @param key 密钥
     * @return 加密工具
     */
    public static HmacUtil buildHmacSHA1(String key) {
        try {
            return new HmacUtil(HMAC_SHA1, key.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建hmac工具
     *
     * @param key 密钥
     * @return 加密工具
     */
    public static HmacUtil buildHmacSHA2(String key) {
        try {
            return new HmacUtil(HMAC_SHA256, key.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * hash计算
     *
     * @param src 原始数据
     * @return 加密后的字节数据
     */
    public byte[] mac(byte[] src) {
        return this.mac.doFinal(src);
    }

    /**
     * hash计算
     *
     * @param src 原始数据
     * @return 加密后的16进制格式数据
     */
    public String mac2Hex(byte[] src) {
        return HexUtil.toHexString(this.mac.doFinal(src)).toLowerCase();
    }
}
