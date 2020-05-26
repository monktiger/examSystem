package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.*;
import com.monktiger.examsystem.mapper.*;
import com.monktiger.examsystem.service.ExamService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.main.client.WMozillaServiceDelegate;

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
    @Autowired
    private CopyMapper copyMapper;
    @Autowired
    private CopyToQuestionMapper copyToQuestionMapper;
    @Autowired
    private ExamToQuestionMapper examToQuestionMapper;
    @Autowired
    private GroupToExamMapper groupToExamMapper;
    @RequestMapping(value = "/getExam",method = RequestMethod.GET)
public Map<String,Object> getExam(@RequestParam("groupId") String groupId, HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        if(groupMapper.selectByPrimaryKey(user.getOpenId(),groupId)!=null){
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
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        int status=examService.solveUserAndExamAssociation(examId,user);
        switch (status){
            case 0:
                modelMap.put("status",-1);
                modelMap.put("msg","内部出错");
                break;
            case 10001:
                modelMap.put("status",10001);
                modelMap.put("msg","跳转到试卷编辑页");
                break;
            case 10002:
                modelMap.put("status",10002);
                modelMap.put("msg","跳转到试卷查看页");
                break;
            case 10003:
                modelMap.put("status",10003);
                modelMap.put("msg","跳转到试卷查看页");
                break;
            case 10004:
                modelMap.put("status",10004);
                modelMap.put("msg","跳转到成绩排行页");
                break;
            case 20001:
                modelMap.put("status",20001);
                modelMap.put("msg","试卷还未开放，请等待");
                break;
            case 20002:
                modelMap.put("status",20002);
                modelMap.put("msg","跳转到作答页");
                break;
            case 20003:
                modelMap.put("status",20003);
                modelMap.put("msg","考试已经结束，可以查看成绩，跳转到错题查看页");
                break;
            case 20004:
                modelMap.put("status",20004);
                modelMap.put("msg","考试已经结束，等待老师批改");
                break;
        }
    }else{
        modelMap.put("status",0);
        modelMap.put("msg","请登录");
    }
    return modelMap;
    }
    @RequestMapping(value = "/answerSubmit",method = RequestMethod.GET)
    public Map<String,Object> answerSubmit(@RequestParam("copyId")int copyId,
                                           @RequestParam("id")int id,
                                           HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        String answer = HttpServletRequestUtil.getString(request,"answer");
        String token=request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token")){
            String userStirng = jedisUtilStrings.get(token);
            JSONObject userJson = JSON.parseObject(userStirng);
            User user = userJson.toJavaObject(User.class);
            Copy copy=copyMapper.selectByPrimaryKey(copyId);
            if(copy==null){
                modelMap.put("status",-1);
                modelMap.put("msg","你无该试卷");
            }
            else{
                if(answer==null){
                    modelMap.put("status",-3);
                    modelMap.put("msg","提交答案为空");
                    return modelMap;
                }
                CopyToQuestion ctq=copyToQuestionMapper.selectByPrimaryKey(copyId,id);
                if(ctq==null){
                    modelMap.put("status",-2);
                    modelMap.put("msg","试卷中不存在该题");
                    return modelMap;
                }
                ExamToQuestion etq=examToQuestionMapper.selectByPrimaryKey(copy.getExamId(),id);
                if(etq.getType()!=5){
                    if(etq.getCurrent().equals(answer)){
                        ctq.setScore(etq.getScore());
                    }else ctq.setScore(0);
                }
                ctq.setAnswer(answer);
                ctq.setAlready(true);
                copyToQuestionMapper.updateByPrimaryKey(ctq);
                modelMap.put("status",1);
                modelMap.put("msg","提交成功");
            }
        }else{
            modelMap.put("status",0);
            modelMap.put("msg","未登录");
        }
        return modelMap;
    }
    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST)
    public Map<String,Object> addQuestion(HttpServletRequest request,@RequestBody String questionString){
        JSONObject questionJson = JSONObject.parseObject(questionString);
        ExamToQuestion examToQuestion = questionJson.toJavaObject(ExamToQuestion.class);
        Map<String,Object> modelMap  = new HashMap<>();
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token")){
            String userStirng = jedisUtilStrings.get(token);
            JSONObject userJson = JSON.parseObject(userStirng);
            User user = userJson.toJavaObject(User.class);
            Exam exam =examMapper.selectByPrimaryKey(examToQuestion.getExamId());
            if(exam==null){
                modelMap.put("status",-1);
                modelMap.put("msg","试卷不存在");
                return modelMap;
            }
            if (exam.getPublisherId()!=user.getOpenId()){
                modelMap.put("status",-2);
                modelMap.put("msg","权限不足");
                return modelMap;
            }
            if(examToQuestion.getId()==null){
                examToQuestionMapper.insertSelective(examToQuestion);
            }else{
                examToQuestionMapper.updateByPrimaryKeySelective(examToQuestion);
            }
            modelMap.put("status",1);
            modelMap.put("msg","题目插入成功");
        }else{
            modelMap.put("status",0);
            modelMap.put("msg","未登录");
        }
        return modelMap;
    }
    @RequestMapping(value = "/createExam" ,method = RequestMethod.POST)
    public Map<String,Object> createExam(HttpServletRequest request,@RequestBody String examString){
        JSONObject examJSON = JSON.parseObject(examString);
        JSONArray groupIdArray = examJSON.getJSONArray("groupId");
        List<String> groupList = groupIdArray.toJavaList(String.class);
        examJSON.remove("groupId");
        Exam exam = examJSON.toJavaObject(Exam.class);
        Map<String,Object> modelMap = new HashMap<>();
        if(exam.getEndTime().compareTo(exam.getBeginTime())==0||exam.getEndTime().compareTo(exam.getBeginTime())>0){
            modelMap.put("status",-1);
            modelMap.put("info","时间设置存在问题");
            return modelMap;
        }
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token")){
            String userStirng = jedisUtilStrings.get(token);
            JSONObject userJson = JSON.parseObject(userStirng);
            User user = userJson.toJavaObject(User.class);
            exam.setPublisherId(user.getOpenId());
            exam.setStatus(0);
            if(exam.getId()==null){
                examMapper.insertSelective(exam);
                groupToExamMapper.UpdateAssociation(exam.getId(),groupList);
            }else {
                Exam oldExam = examMapper.selectByPrimaryKey(exam.getId());
                if(oldExam.getPublisherId()!=user.getOpenId()){
                    modelMap.put("status",-2);
                    modelMap.put("info","无权操作");
                    return modelMap;
                }
                examMapper.updateByPrimaryKey(exam);
                groupToExamMapper.UpdateAssociation(exam.getId(),groupList);
            }
            modelMap.put("status",1);
            modelMap.put("info","创建成功");
        }
        else{
            modelMap.put("status",0);
            modelMap.put("msg","未登录");
        }
        return modelMap;
    }
}
