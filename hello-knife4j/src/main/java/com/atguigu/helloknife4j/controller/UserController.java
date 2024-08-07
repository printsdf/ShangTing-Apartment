package com.atguigu.helloknife4j.controller;

import com.atguigu.helloknife4j.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "用户信息管理")
public class UserController {

    @GetMapping("getById")
    @Operation(summary = "根据ID查询用户信息")
    public User getById(@Parameter(description = "用户ID") @RequestParam("id") Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("zhangsan");
        user.setAge(10);
        return user;
    }
}
