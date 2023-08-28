package com.ruifox.sdqfsyy.enums;

//响应格式枚举类
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    COMMENT_IS_NULL(506,"评论内容为空"),
    FILE_TYPE_ERROR(507,"上传图片格式错误！"),
    USERNAME_ISNULL(508,"用户名不能为空！"),
    NICKNAME_ISNULL(509,"昵称不能为空！"),
    PASSWORD_ISNULL(510,"密码不能为空！"),
    EMAIL_ISNULL(511,"邮箱不能为空！"),
    NICKNAME_EXIST(512,"该昵称已存在！"),
    UPDATE_FALSE(513,"数据更新失败！"),
    JOB_IS_EXECUTING(514,"更新任务已经在执行队列！请耐心等候！"),
    DATA_IS_REFRESHING(515,"数据正在刷新，请耐心等候！"),
    ADMIN_CANNOT_MODIFY(555,"系统预设的超级管理员不可更改");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
