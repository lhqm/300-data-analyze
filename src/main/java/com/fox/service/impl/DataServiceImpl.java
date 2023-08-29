package com.fox.service.impl;

import com.fox.constant.CacheConstant;
import com.fox.entity.ResponseResult;
import com.fox.service.DataService;
import com.fox.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 17:32
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public ResponseResult<?> addAccount(String account) {
        if (redisUtil.sIsMember(CacheConstant.USER_ACCOUNT_QUEUE_UNIQUE,account)) return ResponseResult.errorResult(500,"该ID已在扫描队列！");
        redisUtil.sAdd(CacheConstant.USER_ACCOUNT_QUEUE,account);
        redisUtil.sAdd(CacheConstant.USER_ACCOUNT_QUEUE_UNIQUE,account);
        return new ResponseResult<>(200,"添加成功！");
    }

    @Override
    public ResponseResult<?> changeStatus(boolean b) {
        String status=b?"true":"false";
        redisUtil.set(CacheConstant.PROJECT_ACTIVE_SIGNAL,status);
        return new ResponseResult<>(200,"操作成功");
    }
}
