package com.fox.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fox.annotation.CheckFlag;
import com.fox.constant.CacheConstant;
import com.fox.request.ApiFetch;
import com.fox.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 15:34
 * 扫描一个账户的所有的战绩列表数据，获取对局ID
 */

@Component
@Slf4j
public class DataListScan {
    @Autowired
    private RedisUtil redisUtil;
    private static final HashSet<Integer> jjcMap = new HashSet<>(List.of(314, 315, 320));
    private static final HashSet<Integer> zcMap = new HashSet<>(List.of(502, 503));

    /**
     * 每30秒钟从Redis拉一个用户来获取对局信息
     */
    @Scheduled(cron = "0/30 * * * * ?")
    @CheckFlag
    public void addSession() {
//        不存在数据，直接返回，取消采集
        if (!redisUtil.hasKey(CacheConstant.USER_ACCOUNT_QUEUE)) return;

//        ******开始采集数据*******
//        从队列里pop一个玩家id
        String account = redisUtil.sPop(CacheConstant.USER_ACCOUNT_QUEUE);
        log.info("开始扫描玩家编号为"+account+"的数据...");
//        维护一个计数器，从计数器开始，增加页数，循环读取数据
        int pageNum = 1;
        Set<String> allData=new HashSet<>();
        Set<String> jjcData=new HashSet<>();
        Set<String> zcData=new HashSet<>();
        while (true) {
//            获取数据
            JSONArray recordList = ApiFetch.getRecordList(account, pageNum);
//            为空，已经取完数据，跳出循环
            if (recordList == null) break;
//            不为空的情况下，收集所有数据到集合
            List<String> jjcSession = processUserSessions(recordList, jjcMap);
            List<String> zcSession = processUserSessions(recordList, zcMap);

            allData.addAll(jjcSession);
            allData.addAll(zcSession);

            jjcData.addAll(jjcSession);
            zcData.addAll(zcSession);
//            翻页
            pageNum++;
        }
        log.info("一共扫描了"+pageNum+"页数据，总计条数："+allData.size());
        log.info("战场数据{}条",zcData.size());
        log.info("竞技场/排位数据{}条",jjcData.size());
        String[] values = allData.toArray(new String[0]);
        if (zcData.size()>0) redisUtil.sAdd(CacheConstant.RECORD_ACCOUNT_QUEUE_ZC, zcData.toArray(new String[0]));
        if (jjcData.size()>0) redisUtil.sAdd(CacheConstant.RECORD_ACCOUNT_QUEUE_JJC, jjcData.toArray(new String[0]));
        if (values.length>0) redisUtil.sAdd(CacheConstant.RECORD_ACCOUNT_UNIQUE, values);
    }

    /**
     * 采集数据
     * @param recordList 数据
     * @param typeMap 数据类型
     * @return 符合规范的数据集合
     */
    private List<String> processUserSessions(JSONArray recordList,HashSet<Integer> typeMap){
        List<String> list=new LinkedList<>();
        recordList.forEach(item->{
            JSONObject obj = (JSONObject) item;
//            不被目标场次覆盖的编号，不需要采集
            if (!typeMap.contains(obj.getInteger("mapid"))) return;
//            已经被采集过的编号，不需要再采集
            String sessionId = obj.getString("sessionid");
            if (redisUtil.sIsMember(CacheConstant.RECORD_ACCOUNT_UNIQUE, sessionId)) return;
//            没有采集过的，加入数组
            list.add(sessionId);
        });
        return list;
    }
}
