package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.dto.Score;
import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.*;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import com.monktiger.examsystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/in")
public class PageController {
    @Autowired
    private ExamToQuestionMapper examToQuestionMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CopyMapper copyMapper;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
    @Autowired
    private UserMapper userMapper;
@RequestMapping(value = "/toAdminShow",method = RequestMethod.GET)
    public Map<String,Object> toAdminShow(@RequestParam("examId")int examId, HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        Exam exam = examMapper.selectByPrimaryKey(examId);
        if (exam.getPublisherId()!=user.getOpenId()){
            modelMap.put("status",-1);
            modelMap.put("msg","权限不足");
        }else{
            if(exam.getStatus()==0||exam.getStatus()==1||exam.getStatus()==2){
            modelMap.put("examName",exam.getName());
            //groupId已经分离，这里要改写
            modelMap.put("groupId",exam.getGroupId());
            modelMap.put("beginTime", exam.getBeginTime());
            modelMap.put("endTime",exam.getEndTime());
            modelMap.put("questionList",examToQuestionMapper.selectByPrimaryKey(examId));
            modelMap.put("status",1);
            modelMap.put("msg","试卷渲染成功");}
            else {
                modelMap.put("status",2);
                modelMap.put("msg","考试已经结束，请重定向");
            }
        }
    }
        else{modelMap.put("status",0);
            modelMap.put("msg","未登录");
    }return modelMap;
}
@RequestMapping(value = "/toScoreList",method = RequestMethod.GET)
    public Map<String,Object> toScoreList(@RequestParam("examId")int examId,@RequestParam("groupId")String groupId, HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        Exam exam = examMapper.selectByPrimaryKey(examId);
        if (exam.getPublisherId()!=user.getOpenId()){
            modelMap.put("status",-1);
            modelMap.put("msg","权限不足");
        }else{
            if(exam.getStatus()==3){
                modelMap.put("examName",exam.getName());
                //拼装成绩列表
                List<Score> scoreList = new ArrayList<>();
                Score score = new Score();
                List<Copy> copyList=copyMapper.selectByGroupAndExam(examId,groupId);
                for(Copy c:copyList){
                    User student=userMapper.selectByPrimaryKey(c.getOpenId());
                    score.setStudentName(student.getName());
                    score.setCopyId(c.getCopyId());
                    score.setScore(c.getScore());
                    if(c.getStatus()==3&&c.getJudge()==null){
                        score.setStatus(3);//未批改且未评价
                    }else if(c.getStatus()==3){
                        score.setStatus(1);//未批改
                    }else if(c.getJudge()==null){
                        score.setStatus(2);//未评价
                    }else c.setStatus(4);//上述都以完成
                    scoreList.add(score);
                    Collections.sort(scoreList);
                }
                modelMap.put("scoreList",scoreList);
                modelMap.put("status",1);
                modelMap.put("msg","成绩列表渲染成功");
            }else{
                modelMap.put("status",2);
                modelMap.put("msg","考试已经结束，请重定向");
            }
        }
    }
    else{modelMap.put("status",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;
}
@RequestMapping(value = "/toCopy",method = RequestMethod.GET)
    public Map<String,Object> toCopy(@RequestParam("examId")int examId,HttpServletRequest request){
     Map<String,Object> modelMap = new HashMap<>();
     String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);

    }
    else {
        modelMap.put("status",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;
}
@RequestMapping(value = "/toWrongBook" ,method = RequestMethod.GET)
    public Map<String,Object> toWrongBook(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    Integer copyId = HttpServletRequestUtil.getInt(request,"copyId");
    Integer examId = HttpServletRequestUtil.getInt(request,"examId");
    return modelMap;
}
}
