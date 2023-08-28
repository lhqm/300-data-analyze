package com.fox.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @author He rk
 * @version 1.0
 * @date 2023/3/27 9:43
 * 定时任务配置类，用于配置定时任务的线程池
 */
@Configuration

@Slf4j
public class ScheduleConfig implements SchedulingConfigurer {

//    默认为5，可以自己改配置文件实现
    @Value("${async.poolSize}")
    private Integer poolSize=4;

    /**
     * 创建用于任务调度的异步线程池
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        初始化
        initPoolSize();
//        创建
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(this.poolSize));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("定时任务实例异步线程池已创建，核心线程数为{}",poolSize);
    }
    /**
     * 根据配置文件返回核心线程数
     */
    private void initPoolSize(){
        //获取核心线程数并打印建议消息
        int processors=Runtime.getRuntime().availableProcessors();
        log.info("探测到系统核心数为:{}",processors);
        log.info("理论建议该IO密集模型下的线程池核心线程数最大为:{}", processors*2);
        //策略
//        智能分配
        if (this.poolSize==0){
            log.info("定时任务线程池已智能分配核心线程数");
            this.poolSize=processors*2;
        }
//        非法的默认分配
        else if (this.poolSize<0 || this.poolSize>processors*4){
            log.info("检测到非法值:{}，定时任务线程池已遵从默认配置",this.poolSize);
            this.poolSize=4;
        }
//        默认分配
        else {
            log.info("定时任务线程池已默认分配核心线程数");
        }
    }
}
