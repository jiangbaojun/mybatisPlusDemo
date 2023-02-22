package com.example.demo.mapper.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper  extends BaseMapper<User> {

    List<Map<String, Object>> queryTb1();

    IPage<User> selectMyPage(Page<User> page, @Param("params") Map<String, Object> param);

    /**
     * 请注意，wrapper参数名称必须为‘ew’（Constants.WRAPPER）
     */
    IPage<User> selectWrapperPage(Page<User> page1, @Param(Constants.WRAPPER) QueryWrapper<User> qw);
}
