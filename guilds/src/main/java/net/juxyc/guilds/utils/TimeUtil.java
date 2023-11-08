package net.juxyc.guilds.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum TimeUtil {
    TICK(50, 50),
    MILLISECOND(1, 1),
    SECOND(1000, 1000),
    MINUTE(60000, 60),
    HOUR(3600000, 60),
    DAY(86400000, 24),
    WEEK(604800000, 7);

    public static final int MPT = 50;

    private final int time;

    private final int timeMulti;

    TimeUtil(int time, int timeMulti) {
        this.time = time;
        this.timeMulti = timeMulti;
    }

    public int getMulti() {
        return this.timeMulti;
    }

    public int getTime() {
        return this.time;
    }

    public int getTick() {
        return this.time / 50;
    }

    public int getTime(int multi) {
        return this.time * multi;
    }

    public int getTick(int multi) {
        return getTick() * multi;
    }

    public static long addTime(int seconds) {
        return getTime1() + seconds * 1000L;
    }

    public static long getTime1() {
        return System.currentTimeMillis();
    }

    public static double outTime(long sg) {
        double f = (sg / 10L) / 100.0D;
        return (new BigDecimal(f)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static long fromTime(long start) {
        return start - getTime1();
    }
}
