package com.example.mindjava.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mindjava.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Date 2023-08-10 14:46
 * @Description userMapper
 * @Version 1.0.0
 * @Author liwenbo
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update t_user set login_count = login_count + 1, last_login_time = now() where id = #{id}")
    void incrementLoginCountAndUpdateLastLoginTimeById(@Param("id") Long id);
}
