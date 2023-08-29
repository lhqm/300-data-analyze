package com.fox.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/28 15:45
 */
public class ApiFetch {
    /**
     * 数据列表查询
     * @param account 账户
     * @param pageNum 页数
     * @return 列表数据
     */
    public static JSONArray getRecordList(String account,Integer pageNum){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "__Z_RoleID="+account+"&__Z_PageNum="+pageNum+"");
        Request request = new Request.Builder()
                .url("https://m300battleapp.hhhoo.com/player/full/recent/records")
                .method("POST", body)
                .addHeader("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36 MicroMessenger/7.0.20.1781(0x6700143B) NetType/WIFI MiniProgramEnv/Windows WindowsWechat/WMPF XWEB/6945")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "m300battleapp.hhhoo.com")
                .addHeader("Connection", "keep-alive")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return JSONObject.parseObject(res).getJSONArray("MSG");
        } catch (IOException e) {
            return new JSONArray();
        }
    }

    public static JSONArray getDetail(String session){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://m300battleapp.hhhoo.com/match/records?__Z_TheSessionID="+session)
                .method("POST", body)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "m300battleapp.hhhoo.com")
                .addHeader("Connection", "keep-alive")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            JSONObject msg = JSONObject.parseObject(string).getJSONObject("MSG");
            if (msg==null) return new JSONArray();
            return msg.get("records")==null?new JSONArray():msg.getJSONArray("records");
        } catch (IOException e) {
            return new JSONArray();
        }
    }

    public static void main(String[] args) {
        JSONObject object = new JSONObject();
        object.put("i",1);
        object.put("j",3);
        Double i= object.getDouble("i");
        Double j= object.getDouble("j");
        System.out.println(i/j);
    }
}
