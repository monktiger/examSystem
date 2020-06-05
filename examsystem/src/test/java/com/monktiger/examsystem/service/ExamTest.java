package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.*;
import com.monktiger.examsystem.mapper.*;
import com.monktiger.examsystem.service.impl.ExamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
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
    @Autowired
    ExamToQuestionMapper examToQuestionMapper;
    @Autowired
    CopyMapper copyMapper;
    @Autowired
    CopyToQuestionMapper copyToQuestionMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Test
    public void testQuestion(){
        int total = questionMapper.getTotalQuestion(null,null,null);
        Page page = new Page(1,total);
        List<Question> questionList = questionMapper.getQuestion(page.getStartIndex(),
                page.getPageSize(),null,null,null);
    }

//
//    @Test
//    public void selectExamByGroupAndStatus(){
//        List<Integer> examLists = new ArrayList<>();
//        examLists.add(0,1);
//        List<Exam> exams = examMapper.selectExamByGroupAndStatus(examLists);
//        List<Exam> exams1 = examMapper.selectExamByGroup(examLists);
//        for (Exam exam : exams1){
//            System.out.println(exams.toString());
//            System.out.println(exam.toString());
//        }
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
//
//    @Test
//    public void insertQuestion(){
//        List<ExamToQuestion> examToQuestions = examToQuestionMapper.selectByExamKey(1);
//        List<Copy> copyList = copyMapper.selectByGroupAndExam(1,"aaaaaa");
//        for(Copy copy:copyList){
//            for(ExamToQuestion examq:examToQuestions){
//                copyToQuestionMapper.createQuestion(copy,examq,false);
//            }
//        }
//    }
//
//    @Test
//    public void update(){
//        List<Exam> examList =examMapper.getPrepareExam();
//        examMapper.updateExamListStatus(examList,1);
//    }
//
//    @Test
//    public void testSel(){
//        Copy copy = copyMapper.selectByAssociaiton("oOfAs5CvZ4l_G9bIIODwpUMos_94",13);
//        System.out.println(copy.toString());
//    }
//
//    @Test
//    public void updateScore(){
//        CopyToQuestion ctq = new CopyToQuestion();
//        ctq.setScore(10);
//        ctq.setAlready(false);
//        ctq.setAnswer("hello");
//        ctq.setCopyId(450);
//        ctq.setId(5);
//        copyToQuestionMapper.updateByPrimaryKey(ctq);
//    }
}
