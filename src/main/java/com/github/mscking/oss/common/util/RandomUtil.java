package com.github.mscking.oss.common.util;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机数生成器
 *
 * @author miaosc
 * @date 10/24/2019
 */
public class RandomUtil {

    /**
     * java的进程id
     */
    public static final short PROCESS_ID;

    static {
        String processId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        PROCESS_ID = Short.parseShort(processId);
    }

    /**
     * 自增序列
     */
    private static AtomicInteger sequence = new AtomicInteger(Instant.now().getNano());

    /**
     * UUID生成器
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }


    /**
     * 生成自增序列
     *
     * @return
     */
    public static String nextSequence() {
        short nextInt = (short) (sequence.incrementAndGet());
        //时间+进程Id+自增序列
        Instant now = Instant.now();
        long milli = now.toEpochMilli();
        byte[] bytes = new byte[12];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putLong(0, milli);
        byteBuffer.putShort(8, PROCESS_ID);
        byteBuffer.putShort(10, nextInt);
        return HexUtil.toHexString(bytes);
    }

    /**
     * 生成16个长度的字符串
     *
     * @return
     */
    public static String generate16String() {
        Random random = new Random();
        byte[] bytes = new byte[8];
        //生成随机数字
        random.nextBytes(bytes);
        return HexUtil.toHexString(bytes);
    }

    /**
     * 生成8个长度的字符串
     *
     * @return
     */
    public static String generate8String() {
        Random random = new Random();
        byte[] bytes = new byte[4];
        //生成随机数字
        random.nextBytes(bytes);
        return HexUtil.toHexString(bytes);
    }
}
