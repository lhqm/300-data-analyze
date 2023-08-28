package com.fox.constant;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 15:36
 */
public class CacheConstant {
    /**
     * 玩家编号队列
     */
    public static final String USER_ACCOUNT_QUEUE="queue:user:account";
    /**
     * 玩家编号碰撞队列（该队列保证用户编号只被采集一次）
     */
    public static final String USER_ACCOUNT_QUEUE_UNIQUE="queue:user:unique";
    /**
     * 对局编号队列
     */
    public static final String RECORD_ACCOUNT_QUEUE="queue:record:id";
    /**
     * 对局编号唯一队列
     */
    public static final String RECORD_ACCOUNT_UNIQUE="queue:record:uniqueSet";
}
