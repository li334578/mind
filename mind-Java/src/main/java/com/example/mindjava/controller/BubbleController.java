package com.example.mindjava.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mindjava.bean.ResultBean;
import com.example.mindjava.bean.ResultCode;
import com.example.mindjava.entity.Bubble;
import com.example.mindjava.service.BubbleService;
import com.example.mindjava.util.StpInfoUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Date 2023-08-11 14:13
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/bubble/")
public class BubbleController {
    @Resource
    private BubbleService bubbleService;

    @PutMapping
    public ResultBean doCreateBubble(@RequestBody Bubble bubble) {
        if (bubble.getContent().length() > 500) {
            return ResultBean.error(ResultCode.PARAM_ERROR, "内容长度不能超过200");
        }
        if (Objects.isNull(bubble.getType())) {
            bubble.setType(1);
        }
        bubble.setMasterId(StpInfoUtil.getLoginUserId());
        bubble.setIsDelete(0);
        bubbleService.save(bubble);
        return ResultBean.success();
    }

    @PostMapping("page")
    public ResultBean doPageBubble(@RequestBody Bubble bubble) {
        IPage<Bubble> page = new Page<>(bubble.getCurrentPage(), bubble.getPageSize());
        return ResultBean.success(bubbleService.page(page, new LambdaQueryWrapper<Bubble>()
                .eq(Objects.nonNull(bubble.getIsDelete()), Bubble::getIsDelete, bubble.getIsDelete())
                .eq(Objects.nonNull(bubble.getType()), Bubble::getType, bubble.getType())
                .like(Objects.nonNull(bubble.getContent()), Bubble::getContent, bubble.getContent())
                .orderByDesc(Bubble::getPriorityValue, Bubble::getUpdateTime)));
    }

    @GetMapping("{id}")
    public ResultBean doGetBubble(@PathVariable("id") Long id) {
        return ResultBean.success(bubbleService.getById(id));
    }

    @PostMapping
    public ResultBean doUpdateBubble(@RequestBody Bubble bubble) {
        bubbleService.updateById(bubble);
        return ResultBean.success();
    }

    @DeleteMapping("{id}")
    public ResultBean doDeleteBubble(@PathVariable("id") Long id) {
        Bubble bubble = new Bubble();
        bubble.setId(id);
        bubble.setIsDelete(1);
        bubbleService.updateById(bubble);
        return ResultBean.success();
    }
}
