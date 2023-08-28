package com.fox.task;

import com.fox.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 17:55
 */
@Component
public class DetailScan {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 每五秒钟获取一次详情
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void getDetails(){

    }
}
