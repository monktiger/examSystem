package com.monktiger.examsystem.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/question")
@CrossOrigin
@RestController
public class QuestionBankController {

    @RequestMapping("/addQuestion")
    public Object addQuestion(String title,int type,String category,String current,String answerA,String answerB,String answerC,String answerD){
        return null;
    }

    @RequestMapping("/getQuestionList")
    public Object getQuestion(int pageNum,String search,String category,int type){
        return null;
    }
}
