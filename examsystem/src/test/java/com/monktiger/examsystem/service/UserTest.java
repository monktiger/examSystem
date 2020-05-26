package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        UUID id = UUID.randomUUID();
        User user = new User(id.toString(),"nick_name","name","avaturl",true);
        userService.insertUser(user);
    }

    @Test
    public void testDelete() throws Exception {
       userService.deleteUser("open_ids");
    }

    @Test
    public void testUpdate()throws Exception{
        System.out.println("update");
        User user = new User("open_id","hello_world","names","head_pic",true);
        userService.updateUser(user);
    }

    @Test
    public void testSelect() throws Exception {
        System.out.println("select");
        User user = userService.selectUserByKey("open_ids");
        System.out.println(user);
    }
}
