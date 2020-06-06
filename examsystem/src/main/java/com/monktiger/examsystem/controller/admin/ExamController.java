package com.monktiger.examsystem.controller.admin;

import com.monktiger.examsystem.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin/exam")
public class ExamController {
    @Autowired
    private ExamService examService;
    @RequestMapping(method = RequestMethod.GET)
    public Map<String,Object> getExam(HttpServletRequest request){
        return examService.getExam(request);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteExam(@PathVariable("id")int examId){
        return examService.deleteExam(examId);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.PATCH)
    public Map<String,Object> updateExam(@PathVariable("id")int examId,@RequestBody String jsonString){
        return examService.updateExam(examId,jsonString);
    }
}
