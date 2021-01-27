package com.wecarry.oss.common.util;

import java.time.Instant;

/**
 * 时间点工具
 *
 * @author miaosc
 * @date 2020/10/5 0005
 */
public class TimePoint {


    public static TimePoint create() {
        return new TimePoint();
    }

    /**
     * 时间点
     */
    private Instant instant = Instant.now();

    /**
     * 标记时间
     *
     * @return
     */
    public Instant mark() {
        instant = Instant.now();
        return instant;
    }

    /**
     * 和之前比流逝的时间,单位毫秒
     *
     * @return
     */
    public long elapseMillis() {
        long pastMillis = instant.toEpochMilli();
        Instant now = mark();
        return now.toEpochMilli() - pastMillis;
    }

    private TimePoint() {
    }
}
