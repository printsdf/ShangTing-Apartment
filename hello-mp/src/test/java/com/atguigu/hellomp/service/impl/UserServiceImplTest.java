package com.atguigu.hellomp.service.impl;

import com.atguigu.hellomp.entity.User;
import com.atguigu.hellomp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetById() {
        User user = userService.getById(2);
        System.out.println(user);
    }

    @Test
    public void testSaveOrUpdate() {
        User user1 = userService.getById(2);
        user1.setName("xiaohu");

        User user2 = new User();
        user2.setName("lisi");
        user2.setAge(24);
        user2.setEmail("lisi@email.com");
        userService.saveOrUpdate(user1);
        userService.saveOrUpdate(user2);
    }

    @Test
    public void testSaveBatch() {
        User user1 = new User();
        user1.setName("dongdong");
        user1.setAge(49);
        user1.setEmail("dongdong@email.com");

        User user2 = new User();
        user2.setName("nannan");
        user2.setAge(29);
        user2.setEmail("nannan@email.com");

        List<User> users = List.of(user1, user2);
        userService.saveBatch(users);
    }
}