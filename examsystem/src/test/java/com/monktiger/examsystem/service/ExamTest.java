package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.mapper.ExamMapper;
import com.monktiger.examsystem.service.impl.ExamServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ExamTest {
    @Autowired
    ExamServiceImpl examService;
    @Autowired
    ExamMapper examMapper;

    @Test
    public void selectExamByGroupAndStatus(){
        List<Integer> examLists = new ArrayList<>();
        examLists.add(0,1);
        List<Exam> exams = examMapper.selectExamByGroupAndStatus(examLists);
        List<Exam> exams1 = examMapper.selectExamByGroup(examLists);
        for (Exam exam : exams1){
            System.out.println(exams.toString());
            System.out.println(exam.toString());
        }
    }
}
