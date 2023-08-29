package com.fox.controller;

import com.fox.entity.ResponseResult;
import com.fox.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 17:30
 * 配置驱动事件
 */
@RestController
@RequestMapping("/config")
public class DataController {
    @Autowired
    private DataService dataService;
    @GetMapping("/seed/{account}")
    public ResponseResult<?> toAddAccount(@PathVariable String account){
        return dataService.addAccount(account);
    }
    @GetMapping("/stop")
    public ResponseResult<?> stop(){
        return dataService.changeStatus(false);
    }
    @GetMapping("/restart")
    public ResponseResult<?> start(){
        return dataService.changeStatus(true);
    }
    @GetMapping("/exit")
    public void exit(){
        dataService.exit();
    }
}
