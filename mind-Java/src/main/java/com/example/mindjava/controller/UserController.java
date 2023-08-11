package com.example.mindjava.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mindjava.bean.ResultBean;
import com.example.mindjava.bean.ResultCode;
import com.example.mindjava.entity.User;
import com.example.mindjava.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @Date 2023-08-10 13:18
 * @Description 用户控制器
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private ExecutorService executorService;
    private final MD5 md5 = SecureUtil.md5();

    @GetMapping("list")
    public ResultBean list() {
        return ResultBean.success(userService.list());
    }

    @PostMapping("doLogin")
    public ResultBean<SaTokenInfo> doLogin(@RequestBody User user) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        Optional<User> userOptional = userService.getByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            User dbUser = userOptional.get();
            executorService.execute(() -> userService.incrementLoginCountAndUpdateLastLoginTimeById(dbUser.getId()));
            StpUtil.login(dbUser.getId());
            StpUtil.getSession().set("user", dbUser);
            return ResultBean.success(StpUtil.getTokenInfo());
        }
        return ResultBean.error(ResultCode.PARAM_ERROR, "用户名或密码错误");
    }

    @PostMapping("doRegister")
    public ResultBean doRegister(@RequestBody User user) {
        // 查询username是否已经存在
        if (userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername())) != null) {
            throw new IllegalStateException("用户名已存在");
        }
        // 密码 md5 加密
        user.setPassword(md5.digestHex16(user.getPassword()));
        user.setStatus(1);
        user.setLoginCount(0);
        userService.save(user);
        return ResultBean.success();
    }

    @GetMapping("isLogin")
    public ResultBean<Boolean> isLogin() {
        return ResultBean.success(StpUtil.isLogin());
    }

    @GetMapping("doLogout")
    @SaCheckLogin
    public ResultBean doLogout() {
        StpUtil.logout();
        return ResultBean.success();
    }
}
