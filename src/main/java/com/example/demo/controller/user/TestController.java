package com.example.demo.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.mapper.user.TestMapper;
import com.example.demo.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/t1")
    public int t1(HttpServletRequest request){
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setAge(12);
        user.setBirthday(new Date());
        user.setAdress("shanghai");
        user.setName("xiaoming1004");
        return testMapper.insert(user);
    }
    @RequestMapping("/t2")
    public int t2(HttpServletRequest request){
        return testMapper.deleteById("0074847671d74b5abb44d4804dc9dd10");
    }

    @RequestMapping("/t3")
    public List t3(HttpServletRequest request){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.ge("age", 121)
            .le("age", 125)
                .or()
            .like("name", "xiaoming");
        System.out.println(qw.getSqlSegment());
        System.out.println(qw.getCustomSqlSegment());
        System.out.println(qw.getTargetSql());  //(age >= ? AND age <= ? OR name LIKE ?)
        return testMapper.selectList(qw);
    }

    /**
     * 参考：https://springboot.io/t/topic/2607
     */
    @RequestMapping("/t4")
    public List t4(HttpServletRequest request){
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.and(q -> q.ge(User::getAge, 121).le(User::getAge, 124))
                .or().lt(User::getBirthday, new Date())
                .and(q -> q.like(User::getName, "xiaoming"));
        //注意在sql中，and优先级高于or
        //((age >= ? AND age <= ?) OR birthday < ? AND (name LIKE ?))

//        qw.and((q)->q.ge(User::getAge, 121).le(User::getAge, 124))
//                .or((q)->q.lt(User::getBirthday, new Date()).eq(User::getName, "100"))
//                .and((q) -> q.like(User::getName, "xiaoming").or().like(User::getName, "小明"));
//        //((age >= ? AND age <= ?) OR (birthday < ? AND name = ?) AND (name LIKE ? OR name LIKE ?))

        System.out.println(qw.getSqlSegment());
        System.out.println(qw.getCustomSqlSegment());
        System.out.println(qw.getTargetSql());  //(age >= ? AND age <= ? OR name LIKE ?)
        return testMapper.selectList(qw);
    }

    @RequestMapping("/t5")
    public int t5(HttpServletRequest request){
        User user = new User();
        user.setId("4689b1911a274d519b670bc4d79394b9");
        user.setAge(20);
        user.setBirthday(new Date());
//        //updateById修改数据，必须在User类中@TableId()声明主键，如果主键字段名称为id，也可不声明，建议声明
//        return testMapper.updateById(user);

        LambdaUpdateWrapper<User> uw = new LambdaUpdateWrapper<>();
        uw.eq(User::getAge,"126");
        //uw是where条件，按照user修改数据，但不会修改主键的值
        return testMapper.update(user, uw);
    }

    /**
     * 原生mybatis用法
     */
    @RequestMapping("/t6")
    public List<Map<String, Object>> t6(HttpServletRequest request){
        return testMapper.queryTb1();
    }
}
