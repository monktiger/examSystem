package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.CopyToQuestion;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.CopyMapper;
import com.monktiger.examsystem.mapper.CopyToQuestionMapper;
import com.monktiger.examsystem.mapper.ExamMapper;
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
        int check = copyMapper.checkCopyPublisher(copyId,user.getOpenId());
        if(check==0){
            modelMap.put("status",-1);
            modelMap.put("msg","无权限");
            return modelMap;
        }
        int type = copyToQuestionMapper.checkQuestionType(copyId,id);
        if(type!=5){
            modelMap.put("status",-2);
            modelMap.put("msg","非主观题");
            return modelMap;
        }
        CopyToQuestion copyToQuestion = CopyToQuestionMapper(copyId,id);
        copyToQuestion.setScore(score);
        copyToQuestionMapper.updateByPrimaryKey(copyToQuestion);
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
        int check = copyMapper.checkCopyPublsher(copyId,user.getOpenId());
        if(check==0){
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
