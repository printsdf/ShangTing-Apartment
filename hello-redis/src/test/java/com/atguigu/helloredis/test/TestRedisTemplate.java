package com.atguigu.helloredis.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class TestRedisTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSet() {
        redisTemplate.opsForValue().set("key1", "value1");
    }

    @Test
    public void testGet() {
        String result = (String) redisTemplate.opsForValue().get("key1");
        System.err.println(result);
    }

    @Test
    public void testDel() {
        redisTemplate.delete("key1");
    }
}
