package com.monktiger.examsystem.redis;

import com.monktiger.examsystem.cache.JedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Autowired
    JedisUtil jedisUtil;


    @Test
    public void set(){
    }

    @Test
    public void get(){
    }
}
