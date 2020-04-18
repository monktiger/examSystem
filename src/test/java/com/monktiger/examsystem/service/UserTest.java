package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        User user = new User("open_id","nick_name","name","avaturl",true);
        userService.insertUser(user);
    }

    @Test
    public void testDelete(){
        System.out.println("delete");
    }

    @Test
    public void testUpdate(){
        System.out.println("update");
    }

    @Test
    public void testSelect(){
        System.out.println("select");
    }
}
