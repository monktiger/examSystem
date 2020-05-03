package com.monktiger.examsystem.service;

import com.monktiger.examsystem.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExamTest {
    @Autowired
    ExamServiceImpl examService;

}
