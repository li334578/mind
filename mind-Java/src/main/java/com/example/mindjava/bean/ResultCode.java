package com.example.mindjava.bean;

/**
 * @Date 2023-08-10 13:49
 * @Description 状态码枚举类
 * @Version 1.0.0
 * @Author liwenbo
 */
public enum ResultCode {
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    NOT_LOGIN(401, "未登录"),
    ERROR(500, "错误"),
    PARAM_ERROR(501, "参数异常");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}