package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.UserService;
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
        User user = new User("open_i","nick_name","name","avaturl",true);
        userService.insertUser(user);
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
    }

    @Test
    public void testUpdate(){
        System.out.println("update");
    }

    @Test
    public void testSelect() throws Exception {
        System.out.println("select");
        User user = userService.selectUserByKey("open_ids");
        System.out.println(user);
    }
}
