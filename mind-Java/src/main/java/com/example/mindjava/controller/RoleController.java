package com.example.mindjava.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mindjava.bean.ResultBean;
import com.example.mindjava.entity.Role;
import com.example.mindjava.entity.UserRole;
import com.example.mindjava.service.RoleService;
import com.example.mindjava.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 * 角色表(Role)表控制层
 *
 * @author liwenbo
 * @since 2023-08-18 21:59:46
 */
@RestController
@RequestMapping("role")
@Slf4j
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;

    /**
     * 分页查询所有数据
     *
     * @param role 查询实体
     * @return 所有数据
     */
    @PostMapping("/page")
    public ResultBean<IPage<Role>> pageRole(@RequestBody Role role) {
        Page<Role> rolePage = new Page<>(role.getCurrentPage(), role.getPageSize());
        return ResultBean.success(roleService.page(rolePage, new QueryWrapper<>(role)));
    }

    @PostMapping("/list")
    public ResultBean<List<Role>> listRole(@RequestBody Role role) {
        return ResultBean.success(roleService.list(new QueryWrapper<>(role)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResultBean<Role> selectOne(@PathVariable Long id) {
        return ResultBean.success(roleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param role 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResultBean<Boolean> insert(@RequestBody Role role) {
        // 查询角色名和编码是否重复
        Role dbRole = roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, role.getRoleName())
                .or().eq(Role::getRoleCode, role.getRoleCode()));
        if (Objects.nonNull(dbRole)) {
            throw new IllegalArgumentException("角色名或者编码已存在");
        }
        return ResultBean.success(roleService.save(role));
    }

    /**
     * 修改数据
     *
     * @param role 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResultBean<Boolean> update(@RequestBody Role role) {
        return ResultBean.success(roleService.updateById(role));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResultBean<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        if (CollUtil.isEmpty(idList)) {
            throw new IllegalArgumentException("id列表为空");
        }
        // 角色关联了用户不允许删除
        List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, idList));
        if (CollUtil.isNotEmpty(userRoleList)) {
            throw new IllegalArgumentException("删除的角色已关联了用户");
        }
        return ResultBean.success(roleService.removeByIds(idList));
    }
}

