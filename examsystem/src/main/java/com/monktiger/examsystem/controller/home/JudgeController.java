package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.CopyToQuestion;
import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.CopyMapper;
import com.monktiger.examsystem.mapper.CopyToQuestionMapper;
import com.monktiger.examsystem.mapper.ExamMapper;
import com.monktiger.examsystem.mapper.ExamToQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/judge")
public class JudgeController {
    @Autowired
    private ExamToQuestionMapper examToQuestionMapper;
    @Autowired
    private CopyToQuestionMapper copyToQuestionMapper;
    @Autowired
    private CopyMapper copyMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
@RequestMapping(value = "/score",method = RequestMethod.GET)
    public Map<String,Object> putScore(HttpServletRequest request,
                                       @RequestParam("copyId") int copyId,
                                       @RequestParam("id")int id,
                                       @RequestParam("score")int score){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
//    int copyId = HttpServletRequestUtil.getInt(request,"copyId");
//    int id  = HttpServletRequestUtil.getInt(request,"id");
//    int score = HttpServletRequestUtil.getInt(request,"score");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userString = jedisUtilStrings.get(token);
        JSONObject userJSON = JSON.parseObject(userString);
        User user = userJSON.toJavaObject(User.class);
        //int check = copyMapper.checkCopyPublisher(copyId,user.getOpenId());
        Copy copy = copyMapper.selectByPrimaryKey(copyId);
        Exam exam = examMapper.selectByPrimaryKey(copy.getExamId());
        if(!exam.getPublisherId().equals(user.getOpenId())){
            modelMap.put("status",-1);
            modelMap.put("msg","无权限");
            return modelMap;
        }
//        int type = copyToQuestionMapper.checkQuestionType(copyId,id);
        int type = examToQuestionMapper.selectByPrimaryKey(exam.getId(),id).getType();
        if(type!=5){
            modelMap.put("status",-2);
            modelMap.put("msg","非主观题");
            return modelMap;
        }
        CopyToQuestion copyToQuestion = new CopyToQuestion(copyId,id);
        copyToQuestion.setScore(score);
        CopyToQuestion copyToQuestion1=copyToQuestionMapper.selectByPrimaryKey(copyId,id);
        Integer oldScore=0;
        if(copyToQuestion1!=null)
          oldScore=copyToQuestion1.getScore();
        if(oldScore==null){
            oldScore=1;
        }
        copyToQuestionMapper.updateByPrimaryKey(copyToQuestion);
        int totalScore = copy.getScore();
        totalScore = totalScore-oldScore;
        totalScore = totalScore+score;
        copy.setScore(totalScore);
        copyMapper.updateByPrimaryKeySelective(copy);
        int count=copyToQuestionMapper.selectCopyIdScore(copyId);
        if(count==0){
            copy.setStatus(2);
            copyMapper.updateByPrimaryKeySelective(copy);
        }
        modelMap.put("status",1);
        modelMap.put("msg","分数更新成功");
    }else {
        modelMap.put("status",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;

}
@RequestMapping(value = "/judge",method = RequestMethod.GET)
    public Map<String,Object> putJudge(HttpServletRequest request,
                                       @RequestParam("copyId")int copyId,
                                       @RequestParam("judge")String judge){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userString = jedisUtilStrings.get(token);
        JSONObject userJSON = JSON.parseObject(userString);
        User user = userJSON.toJavaObject(User.class);
        Copy copy = copyMapper.selectByPrimaryKey(copyId);
        Exam exam = examMapper.selectByPrimaryKey(copy.getExamId());
        if(!exam.getPublisherId().equals(user.getOpenId())){
            modelMap.put("status",-1);
            modelMap.put("msg","无权限");
            return modelMap;
        }
        copyMapper.updateJudge(copyId,judge);
        modelMap.put("status",1);
        modelMap.put("msg","评论成功");
    }else {
        modelMap.put("success",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;
}
}
