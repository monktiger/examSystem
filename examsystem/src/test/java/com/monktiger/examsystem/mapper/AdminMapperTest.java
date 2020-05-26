package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminMapperTest {
    @Autowired
    AdminMapper adminMapper;

    @Test
    public void delete(){
        Admin admin = new Admin();
        admin.setUsername("123");
        adminMapper.deleteByPrimaryKey(admin);
    }

    @Test
    public void insert(){
        Admin admin = new Admin();
        adminMapper.insert(admin);
    }

    @Test
    public void insertSelective(){
        Admin admin = new Admin();
    }
}
