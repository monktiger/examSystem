package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class UserTest {
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    ExamService examService;
//
//    @Test
//    public void testAdd() throws Exception {
//        System.out.println("add");
//        UUID id = UUID.randomUUID();
//        User user = new User(id.toString(),"nick_name","name","avaturl",true);
//        userService.insertUser(user);
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//       userService.deleteUser("open_ids");
//    }
//
//    @Test
//    public void testUpdate()throws Exception{
//        System.out.println("update");
//        User user = new User("open_id","hello_world","names","head_pic",true);
//        userService.updateUser(user);
//    }
//
//    @Test
//    public void testSelect() throws Exception {
//        System.out.println("select");
//        User user = userService.selectUserByKey("oOfAs5CvZ4l_G9bIIODwpUMos_94");
//        System.out.println(user.toString());
//    }
//
//    @Test
//    public void execute(){
//        User user = new User();
//        user.setOpenId("oOfAs5CvZ4l_G9bIIODwpUMos_94");
//        List<Exam> exams = examService.excuteExamList("5ef19a",user);
//        for (Exam exam : exams){
//            System.out.println(exam.toString());
//        }
//    }
}
