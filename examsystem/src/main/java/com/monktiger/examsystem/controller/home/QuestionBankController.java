package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Page;
import com.monktiger.examsystem.entity.Question;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.QuestionMapper;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/question")
@CrossOrigin
@RestController
public class QuestionBankController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;

    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST)
    public Map<String,Object> addQuestion(HttpServletRequest request, @RequestBody String jsonString){
        Map<String,Object> modelMap = new HashMap<>();
        //token机制
        String token = request.getHeader("token");
        if(token!=null) {
            JSONObject questionJSON = JSON.parseObject(jsonString);
            Question question = questionJSON.toJavaObject(Question.class);
           String userStirng = jedisUtilStrings.get(token);
           JSONObject userJson = JSON.parseObject(userStirng);
           User user = userJson.toJavaObject(User.class);
           question.setOpenId(user.getOpenId());
           questionMapper.insertSelective(question);
           modelMap.put("status",1);
           modelMap.put("msg","题目提交到题库成功");
        }
        else{
            modelMap.put("status",0);
            modelMap.put("msg","用户未登录");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getQuestionList",method = RequestMethod.GET)
    public Map<String,Object> getQuestion(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Integer pageNum = HttpServletRequestUtil.getInt(request,"pageNum");
        String search = HttpServletRequestUtil.getString(request,"search");
        String category = HttpServletRequestUtil.getString(request,"category");
        Integer type = HttpServletRequestUtil.getInt(request,"type");
        if(pageNum==null){
            pageNum = 1;
        }
        int total = questionMapper.getTotalQuestion(search,category,type);
        Page page = new Page(pageNum,total);
        List<Question> questionList = questionMapper.getQuestion(page.getStartIndex(),
                page.getPageSize(),search,category,type);
        modelMap.put("pageNum",pageNum);
        modelMap.put("pages",page.getTotalPage());
        modelMap.put("total",total);
        modelMap.put("questionList",questionList);
        modelMap.put("status",1);
        modelMap.put("msg","获取题目列成功");
        return modelMap;
    }

}
