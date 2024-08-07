package com.atguigu.hellomp.wrapper;

import com.atguigu.hellomp.entity.User;
import com.atguigu.hellomp.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LambdaWrapperTest {

    @Autowired
    private UserService userService;

    @Test
    public void testLambdaQueryWrapper() {
        //查询name=Tom的所有用户
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getName, "Tom");
        List<User> list = userService.list(userLambdaQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testLambdaUpdateWrapper() {
        //将name=Tom的用户的邮箱改为Tom@tom.com
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getName, "Tom").set(User::getEmail, "Tom@tom.com");
        userService.update(userLambdaUpdateWrapper);

    }
}
