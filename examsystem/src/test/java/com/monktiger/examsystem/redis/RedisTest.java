package com.monktiger.examsystem.redis;

import com.monktiger.examsystem.cache.JedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    JedisUtil jedisUtil;

    /**
     * 插入缓存数据
     */
    @Test
    public void set(){
//        jedisUtil.KEYS.;

    }

    /**
     * 读取缓存数据
     */
    @Test
    public void get(){

    }
}
