package com.zyb.toc.service.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * javadoc id 相关的utils
 *
 * @author zhang yebai
 * @date 2020/5/11 11:10
 * @version 1.0.0
 */
@SuppressWarnings(value = "unused")
public final class IdUtils {

    private IdUtils(){

    }

    private static final SnowFlake SNOW_FLAKE_ID_WORKER_0 = new SnowFlake(0L, 0L);

    private static final SnowFlake SNOW_FLAKE_ID_WORKER_1 = new SnowFlake(0L, 1L);

    private static final SnowFlake SNOW_FLAKE_ID_WORKER_2 = new SnowFlake(0L, 2L);

    private static final SnowFlake SNOW_FLAKE_ID_WORKER_3 = new SnowFlake(0L, 3L);

    private static final SnowFlake SNOW_FLAKE_ID_WORKER_4 = new SnowFlake(0L, 4L);

    /**
     * javadoc snowFlakeId
     * @apiNote 简单的snowflake id, 由work 0 直接产生
     *
     * @return java.lang.String
     * @author zhang yebai
     * @date 2020/5/11 11:13
     */
    public static String id(){
        return String.valueOf(SNOW_FLAKE_ID_WORKER_0.nextId());
    }

    /**
     * javadoc balancedSnowFlakeId
     * @apiNote 负载的id生成, 避免短时间id冲突
     * ThreadLocalRandom instead of Random
     *
     * @return java.lang.String
     * @author zhang yebai
     * @date 2020/5/11 11:17
     */
    public static String balancedId(){
        int idx = ThreadLocalRandom.current().nextInt(100) % 5;
        switch (idx){
            case 0:
                return String.valueOf(SNOW_FLAKE_ID_WORKER_0.nextId());
            case 1:
                return String.valueOf(SNOW_FLAKE_ID_WORKER_1.nextId());
            case 2:
                return String.valueOf(SNOW_FLAKE_ID_WORKER_2.nextId());
            case 3:
                return String.valueOf(SNOW_FLAKE_ID_WORKER_3.nextId());
            case 4:
                return String.valueOf(SNOW_FLAKE_ID_WORKER_4.nextId());
            default:
                throw new RuntimeException("unknown id instance");
        }
    }
}


/**
 * javadoc SnowFlake
 * <p>
 *     snow flake id impl
 * <p>
 * @author zhang yebai
 * @date 2021/4/6 2:51 PM
 * @version 1.0.0
 **/
@SuppressWarnings(value = "unused")
final class SnowFlake {
    private static final long DEFAULT_DATA_CENTER_ID = makeDataCenterId();

    /**
     * 起始的时间戳 2020-05-03 00:05:03
     */
    private static final long START_STAMP = 1588435503000L;

    /**
     * 每一部分占用的位数
     * SEQUENCE_BITS 序列号占用的位数
     * MACHINE_BITS 机器标识占用的位数
     * DATA_CENTER_BITS 数据中心占用的位数
     */
    private static final long SEQUENCE_BITS = 12;
    private static final long MACHINE_BITS = 5;
    private static final long DATA_CENTER_BITS = 5;

    /**
     * 每一部分的最大值
     * MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BITS);
     * MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BITS);
     * MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BITS);
     */
    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BITS);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_LEFT = SEQUENCE_BITS + MACHINE_BITS;
    private static final long TIME_STAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BITS;

    /**
     * 数据中心
     **/
    private final long dataCenterId;

    /**
     * 机器标识
     **/
    private final long machineId;

    /**
     * 序列号
     **/
    private long sequence = 0L;

    /**
     * 上一次时间戳
     **/
    private long lastStamp = -1L;

    /**
     * 不要使用公平锁
     **/
    private final Lock lock = new ReentrantLock();

    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public SnowFlake(long machineId) {
        this(DEFAULT_DATA_CENTER_ID, machineId);
    }

    /**
     * javadoc makeDataCenterId
     * @apiNote 有碰撞的可能
     *
     * @return long
     * @author zhang yebai
     * @date 2020/11/3 14:05
     */
    private static long makeDataCenterId() {
        try {
            final String ip = Inet4Address.getLocalHost().getHostAddress();
            final var lip = ipV4ToLong(ip);
            return lip % 32;
        } catch (UnknownHostException ignore) {
            // 如果获取失败，则使用随机数备用
        }
        return new Random().nextInt(100) % 32;
    }

    /**
     *  javadoc ipV4ToLong
     *  @apiNote 点分十进制字符串ip地址转换成long
     *           将ip低位存储至数据的高位, 降低%32撞work id的风险
     *
     *  @param  address 点分十进制字符串ip地址
     *  @return  long
     *  @author zhang yebai
     *  @date 2020/5/18 12:06
     */
    private static long ipV4ToLong(String address){
        if(Objects.isNull(address) || address.length() == 0){
            throw new RuntimeException("long ipV4ToLong(String address) cant apply to illegal 'address' = " + address);
        }
        String[] parts = address.split("\\.");
        if(parts.length != 4){
            throw new RuntimeException("long ipV4ToLong(String address) cant apply to illegal 'address' = " + address);
        }
        return (Long.parseLong(parts[3]) << 24) + (Long.parseLong(parts[2]) << 16) + (Long.parseLong(parts[1]) << 8) + (Long.parseLong(parts[0]));
    }

    public long nextId(){
        lock.lock();
        try{
            return this.id();
        }finally {
            lock.unlock();
        }
    }

    public long syncNextId(){
        synchronized (this){
            return this.id();
        }
    }

    private long id(){
        long stamp = System.currentTimeMillis();
        if(stamp < this.lastStamp){
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if(stamp == this.lastStamp){
            this.sequence = (this.sequence + 1) & MAX_SEQUENCE;
            if(this.sequence == 0L){
                stamp = nextStamp();
            }
        }else{
            this.sequence = 0L;
        }
        this.lastStamp = stamp;
        return (stamp - START_STAMP) << TIME_STAMP_LEFT
                | this.dataCenterId << DATA_CENTER_LEFT
                | this.machineId << MACHINE_LEFT
                | this.sequence;
    }

    private long nextStamp(){
        long stamp = System.currentTimeMillis();
        while (stamp <= lastStamp) {
            stamp = System.currentTimeMillis();
        }
        return stamp;
    }
}