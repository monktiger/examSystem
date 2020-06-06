package com.monktiger.examsystem.controller.admin;

import com.monktiger.examsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @RequestMapping(method = RequestMethod.GET)
    public Map<String,Object> getQuestion(HttpServletRequest request){
        return questionService.getQuestion(request);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Map<String,Object> postQuestion(@RequestBody String jsonString){
        return questionService.putQuestion(jsonString);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteQuestion(@PathVariable("id")int questionId){
        return questionService.deleteQuestion(questionId);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.PATCH)
    public Map<String,Object> updateQuestion(@PathVariable("id")int questionId,@RequestBody String jsonString){
        return questionService.updateQuestion(questionId,jsonString);
    }
}
