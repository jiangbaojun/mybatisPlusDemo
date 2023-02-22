package com.example.demo.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.model.user.User;
import com.example.demo.service.user.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/test1")
public class Test1Controller {

    @Autowired
    private TestService testService;

    @RequestMapping("/t1")
    public boolean t1(HttpServletRequest request){
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setAge(12);
        user.setBirthday(new Date());
        user.setAdress("shanghai");
        user.setName("xiaoming1004");
        List<User> list = Arrays.asList(
                new User("1","n1",1,new Date()),
                new User("2","n2",2,new Date()),
                new User("3","n3",3,new Date()),
                new User("4","n4",4,new Date()),
                new User("5","n5",5,new Date())
        );
        return testService.saveBatch(list);
    }

    /**
     * 分页
     */
    @RequestMapping("/t2")
    public IPage<User> t2(HttpServletRequest request){
        IPage<User> iPage = testService.page(new Page<User>(1,10));
        return iPage;
    }

    /**
     * 分页-自定义sql
     * 自定义sql是否分页，根据mapper的返回值确定。com.baomidou.mybatisplus.core.override.MybatisMapperMethod#execute(org.apache.ibatis.session.SqlSession, java.lang.Object[])
     * 分页参数获取，根据mapper参数数组中有没有IPage类型确定。com.baomidou.mybatisplus.core.override.MybatisMapperMethod#executeForIPage(org.apache.ibatis.session.SqlSession, java.lang.Object[])
     */
    @RequestMapping("/t3")
    public IPage<User> t3(HttpServletRequest request){
        Map<String,Object> param = new HashMap<>();
        param.put("name", "x");
        IPage<User> iPage = testService.selectMyPage(new Page<User>(1,5), param);
        return iPage;
    }

    /**
     * 分页-自定义sql,wrapper条件
     */
    @RequestMapping("/t4")
    public IPage<User> t4(HttpServletRequest request){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.ge("age", 0).le("age", 20);
        IPage<User> iPage = testService.selectWrapperPage(new Page<User>(1,5), qw);
        return iPage;
    }

    public static void main(String[] args) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.ge("age", 0).le("age", 20);
        qw.and(i->i.eq("address","beijing").or().le("salary", 300));
        qw.like("desc", "xiaoming%");
        System.out.println(qw.getSqlSegment());
        System.out.println(qw.getTargetSql());

//        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>(new User("1","2",2,new Date()));
//        lqw.eq(User::getAdress,"beijing")
//                .and(q -> q.ge(User::getAge, 121).le(User::getAge, 124))
//                .or().lt(User::getBirthday, new Date())
//                .and(q -> q.like(User::getName, "xiaoming"));
//        System.out.println(lqw.getTargetSql());


    }

}
