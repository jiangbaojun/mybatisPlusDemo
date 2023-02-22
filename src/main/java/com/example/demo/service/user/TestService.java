package com.example.demo.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.user.TestMapper;
import com.example.demo.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestService extends ServiceImpl<TestMapper, User> {

    @Autowired
    private TestMapper testMapper;

    public IPage<User> selectMyPage(Page<User> page, Map<String, Object> param) {
        return testMapper.selectMyPage(page, param);
    }

    public IPage<User> selectWrapperPage(Page<User> page, QueryWrapper<User> qw) {
        return testMapper.selectWrapperPage(page, qw);
    }
}
