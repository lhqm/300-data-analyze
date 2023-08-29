package com.fox.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fox.annotation.CheckFlag;
import com.fox.constant.CacheConstant;
import com.fox.entity.ExportedSessionData;
import com.fox.request.ApiFetch;
import com.fox.util.ExcelUtil;
import com.fox.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 17:55
 */
@Component
@Slf4j
public class DetailScan {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 每五秒钟获取一次详情,每次zc和jjc数据各分析一条
     */
    @Scheduled(cron = "0/5 * * * * ?")
    @CheckFlag
    public void getDetails(){
//        处理jjc数据
        if (redisUtil.hasKey(CacheConstant.RECORD_ACCOUNT_QUEUE_JJC)){
            JSONArray data = ApiFetch.getDetail(redisUtil.sPop(CacheConstant.RECORD_ACCOUNT_QUEUE_JJC));
//            计算比率
            JSONArray array = calcRates(data);
            List<ExportedSessionData> dataList = array.toJavaList(ExportedSessionData.class);
            ExcelUtil.writeToExcel("竞技场对局分析数据.xlsx",dataList);
        }
        //        处理zc数据
        if (redisUtil.hasKey(CacheConstant.RECORD_ACCOUNT_QUEUE_ZC)){
            JSONArray data = ApiFetch.getDetail(redisUtil.sPop(CacheConstant.RECORD_ACCOUNT_QUEUE_ZC));
//            计算比率
            JSONArray array = calcRates(data);
            List<ExportedSessionData> dataList = array.toJavaList(ExportedSessionData.class);
            ExcelUtil.writeToExcel("战场对局分析数据.xlsx",dataList);
        }
    }

    /**
     * 计算各种比率，获取承伤比和输出比、参团率等
     * @param data 数据
     * @return 计算后得到的数据
     */
    public JSONArray calcRates(JSONArray data){
//        初始化总线数据(按照damage、suffer、teamScore排列)
        Double[] winner=new Double[]{(double) 0, (double) 0, (double) 0};
        Double[] loser=new Double[]{(double) 0, (double) 0, (double) 0};
//        创建一个set来存放玩家ID。如果玩家ID没有被添加到Redis，进行添加操作
        List<String> ids=new LinkedList<>();
//        第一次循环，获取到总数据
        data.forEach(item->{
            JSONObject obj = (JSONObject) item;
            ids.add(obj.getString("roleid"));
            if (obj.getInteger("result") == 1) {
                winner[0]+=obj.getDouble("damages");
                winner[1]+=obj.getDouble("sufferdamages");
                winner[2]+=obj.getDouble("kills");
            }else {
                loser[0]+=obj.getDouble("damages");
                loser[1]+=obj.getDouble("sufferdamages");
                loser[2]+=obj.getDouble("kills");
            }
        });
//        将ID对比存放到数据库节点
        List<String> collect = ids.stream().filter(s -> !redisUtil.sIsMember(CacheConstant.RECORD_ACCOUNT_UNIQUE, s)).collect(Collectors.toList());
        if (collect.size()>0){
            redisUtil.sAdd(CacheConstant.USER_ACCOUNT_QUEUE,collect.toArray(new String[0]));
            redisUtil.sAdd(CacheConstant.USER_ACCOUNT_QUEUE_UNIQUE,collect.toArray(new String[0]));
        }
//        第二次循环，开始计算比率
        data.forEach(item->{
            JSONObject obj = (JSONObject) item;
            if (obj.getInteger("result") == 1) {
                obj.put("damageRate",obj.getDouble("damages")/winner[0]);
                obj.put("sufferRate",obj.getDouble("sufferdamages")/winner[1]);
                obj.put("joinRate",(obj.getDouble("kills")+obj.getDouble("assists"))/winner[2]);
            }else {
                obj.put("damageRate",obj.getDouble("damages")/loser[0]);
                obj.put("sufferRate",obj.getDouble("sufferdamages")/loser[1]);
                obj.put("joinRate",(obj.getDouble("kills")+obj.getDouble("assists"))/loser[2]);
            }
        });

        return data;
    }
}
