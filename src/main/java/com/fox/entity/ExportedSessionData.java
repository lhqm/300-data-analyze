package com.fox.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/8/29 9:43
 * 需要导出为Excel进行分析的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportedSessionData {
    @ExcelProperty("连续击杀")
    private String continuouskill;
    @ExcelProperty("三杀")
    private String ownage;
    @ExcelProperty("首胜")
    private String firstwin;
    @ExcelProperty("蓝buff获取数")
    private String bluebuff;
    @ExcelProperty("死亡次数")
    private String deaths;
    @ExcelProperty("四杀")
    private String ultrakill;
    @ExcelProperty("隐藏分")
    private String elo;
    @ExcelProperty("建筑伤害")
    private String builddamages;
    @ExcelProperty("未知字段【huangup】")
    private String huangup;
    @ExcelProperty("战斗得分")
    private String battlescore;
    @ExcelProperty("推塔数")
    private String towerdestroy;
    @ExcelProperty("祭坛数")
    private String sacrificialaltar;
    @ExcelProperty("击杀")
    private String kills;
    @ExcelProperty("治疗量")
    private String treatment;
    @ExcelProperty("造成伤害")
    private String damages;
    @ExcelIgnore
    private String banheroid;
    @ExcelProperty("未知字段【attackcitymonster】")
    private String attackcitymonster;
    @ExcelProperty("对局情况（1胜利0失败）")
    private String result;
    @ExcelProperty("英雄熟练度")
    private String heroscore;
    @ExcelProperty("助攻")
    private String assists;
    @ExcelProperty("光神击杀数")
    private String bigdragon;
    @ExcelProperty("承伤百分比")
    private String sufferdamages;
    @ExcelProperty("我的得分（除以10就是KDA）")
    private String myscore;
    @ExcelProperty("是否mvp（0否1是）")
    private String mvp;
    @ExcelProperty("红buff获取数")
    private String redbuff;
    @ExcelProperty("超神（0否1是）")
    private String holyshit;
    @ExcelProperty("是否svp（0否1是）")
    private String svp;
    @ExcelProperty("五杀")
    private String rampage;
    @ExcelProperty("小兵击杀数")
    private String killsoldier;
    @ExcelProperty("狐妖击杀数")
    private String smalldragon;
    @ExcelProperty("经济")
    private String money;
    @ExcelProperty("小怪击杀数")
    private String killmonster;
    @ExcelProperty("英雄等级")
    private String herolevel;
    @ExcelProperty("输出比")
    private String damageRate;
    @ExcelProperty("承伤比")
    private String sufferRate;
    @ExcelProperty("参团率")
    private String joinRate;
}
