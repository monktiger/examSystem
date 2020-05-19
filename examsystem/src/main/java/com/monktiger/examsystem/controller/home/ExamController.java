package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.ExamMapper;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.service.ExamService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamService examService;
    @RequestMapping(value = "/getExam",method = RequestMethod.GET)
public Map<String,Object> getExam(@RequestParam("groupId") String groupId, HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        if(groupMapper.checkGroup(user.getOpenId(),groupId)!=0){
            List<Exam> examList =examService.excuteExamList(groupId,user);
            modelMap.put("examList",examList);
            modelMap.put("status",1);
            modelMap.put("msg","获取试卷列表成功");
        }else{
            modelMap.put("status",-1);
            modelMap.put("msg","您不在该组内");
        }
    }else{
        modelMap.put("status",0);
        modelMap.put("msg","请登录");
    }
    return modelMap;
}
@RequestMapping(value = "/inExam",method = RequestMethod.GET)
public Map<String,Object> inExam(@RequestParam("examId")int examId,HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        int status=examService.solveUserAndExamAssociation(examId,user);
        switch (status){
            case 1:
        }
    }else{
        modelMap.put("status",0);
        modelMap.put("msg","请登录");
    }
    return modelMap;
    }
@RequestMapping
}
