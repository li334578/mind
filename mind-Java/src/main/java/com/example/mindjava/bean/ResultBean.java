package com.example.mindjava.bean;

import lombok.Data;

/**
 * rest接口通用返回值数据结构
 *
 * @param <T>
 */
@Data
public class ResultBean<T> {
    //接口状态（成功还是失败）
    private Boolean success;
    //错误码
    private int code;
    //提示信息
    private String msg;
    //数据
    private T data;

    public static ResultBean success() {
        ResultBean result = new ResultBean<>();
        result.setSuccess(Boolean.TRUE);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> ResultBean<T> success(T data) {
        ResultBean<T> result = new ResultBean<>();
        result.setSuccess(Boolean.TRUE);
        result.setData(data);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> ResultBean<T> error(ResultCode resultCode) {
        return error(resultCode, resultCode.getMessage(), null);
    }

    public static <T> ResultBean<T> error(ResultCode resultCode, String message) {
        return error(resultCode, message, null);
    }

    public static <T> ResultBean<T> error(ResultCode resultCode, String message, T data) {
        ResultBean<T> result = new ResultBean<>();
        result.setSuccess(Boolean.FALSE);
        result.setCode(resultCode.getCode());
        result.setMsg(message);
        result.setData(data);
        return result;
    }
}