package com.example.mindjava.controller;

import com.example.mindjava.bean.ResultBean;
import com.example.mindjava.controller.dto.ChangeAllowRegisterBean;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Date 2023-08-14 13:32
 * @Description 管理控制器
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/manager/")
public class ManagerController {
    @Resource
    private Redisson redisson;

    private static final String allowRegisterKey = "manager:allow:register";

    @GetMapping("allowRegister")
    public ResultBean<Boolean> getAllowRegister() {
        RBucket<Boolean> bucket = redisson.getBucket(allowRegisterKey);
        if (Objects.isNull(bucket.get())) {
            bucket.set(true);
        }
        return ResultBean.success(bucket.get());
    }

    @PostMapping("changeAllowRegister")
    public ResultBean changeAllowRegister(@RequestBody ChangeAllowRegisterBean changeAllowRegisterBean) {
        RBucket<Boolean> bucket = redisson.getBucket(allowRegisterKey);
        bucket.set(changeAllowRegisterBean.getAllowRegister());
        return ResultBean.success();
    }
}
