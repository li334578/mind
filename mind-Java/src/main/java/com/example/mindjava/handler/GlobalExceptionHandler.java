package com.example.mindjava.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.mindjava.bean.ResultBean;
import com.example.mindjava.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Date 2023-08-10 13:40
 * @Description 全局异常处理器
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResultBean handleIllegalArgumentException(IllegalArgumentException e) {
        return ResultBean.error(ResultCode.PARAM_ERROR, e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public ResultBean handleNotLoginException() {
        return ResultBean.error(ResultCode.NOT_LOGIN);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultBean handleRuntimeException(RuntimeException e) {
        log.error("全局异常：{}", e.getMessage());
        return ResultBean.error(ResultCode.ERROR, e.getMessage());
    }
}
