package com.fox.service;

import com.fox.entity.ResponseResult;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 17:31
 */
public interface DataService {
    ResponseResult<?> addAccount(String account);

    ResponseResult<?> changeStatus(boolean b);

    void exit();
}
