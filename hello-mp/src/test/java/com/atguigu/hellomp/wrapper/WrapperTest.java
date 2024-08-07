package com.atguigu.hellomp.wrapper;

import com.atguigu.hellomp.entity.User;
import com.atguigu.hellomp.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.function.Consumer;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserService userService;

    @Test
    public void testQueryWrapper() {
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", "Tom");

        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.like("email", "baomidou.com");

        QueryWrapper<User> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.orderByDesc("age");

        QueryWrapper<User> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.ge("age", 20).le("age", 30);

        QueryWrapper<User> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.lt("age", 20).or().gt("age", 30);

        QueryWrapper<User> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.like("email", "baomidou.com").and(userQueryWrapper -> userQueryWrapper.lt("age", 30).or().gt("age", 40));


        List<User> list = userService.list(queryWrapper6);
        list.forEach(System.out::println);
    }

    @Test
    public void testUpdateWrapper() {
        //将name=Tom的用户的email改为Tom@baobidou.com
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("name", "Tom").set("email", "Tom@baobidou.com");

        userService.update(userUpdateWrapper);
    }
}
