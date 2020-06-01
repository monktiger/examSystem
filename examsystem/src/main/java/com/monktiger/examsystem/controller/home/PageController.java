package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.dto.CopyQuestion;
import com.monktiger.examsystem.dto.Score;
import com.monktiger.examsystem.entity.*;
import com.monktiger.examsystem.mapper.*;
import com.monktiger.examsystem.service.ExamService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private GroupToExamMapper groupToExamMapper;
    @Autowired
    private CopyToQuestionMapper copyToQuestionMapper;
    @Autowired
    private ExamService examService;
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
            List<String> groupList = groupToExamMapper.selectByExamId(examId);
            modelMap.put("examId",examId);
            modelMap.put("groupId",groupList);
            modelMap.put("beginTime", exam.getBeginTime());
            modelMap.put("endTime",exam.getEndTime());
            modelMap.put("questionList",examToQuestionMapper.selectByExamKey(examId));
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
        if (!exam.getPublisherId().equals(user.getOpenId())){
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
                modelMap.put("msg","考试还未结束，请重定向");
            }
        }
    }
    else{modelMap.put("status",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;
}

    /**
     * 进入学生试卷
     * @param examId
     * @param request
     * @return
     */
    @RequestMapping(value = "/toCopy",method = RequestMethod.GET)
    public Map<String,Object> toCopy(@RequestParam("examId")int examId,HttpServletRequest request){
     Map<String,Object> modelMap = new HashMap<>();
     String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        Exam exam = examMapper.selectByPrimaryKey(examId);
        if(exam==null){
            modelMap.put("status",-1);
            modelMap.put("msg","exam不存在");
            return modelMap;
        }
        Copy copy=copyMapper.selectByAssociaiton(user.getOpenId(),examId);
        if(copy==null){
            modelMap.put("status",-2);
            modelMap.put("msg","copy不存在");
            return modelMap;
        }
        if(copy.getStatus()!=1&&exam.getStatus()!=2){
            modelMap.put("status",-3);
            modelMap.put("msg","非考试时间");
            return modelMap;
        }
        modelMap.put("examName",exam.getName());
        modelMap.put("beginTime",exam.getBeginTime());
        modelMap.put("endTime",exam.getEndTime());
        modelMap.put("copyId",copy.getCopyId());
        List<CopyToQuestion> copyToQuestionList = copyToQuestionMapper.selectByCopyId(copy.getCopyId());
        List<ExamToQuestion> examToQuestionList = examToQuestionMapper.selectByExamKey(examId);
        List<CopyQuestion> copyQuestionList = new ArrayList<>();
        for(int i = 0;i<copyToQuestionList.size();i++){
            CopyQuestion copyQuestion = new CopyQuestion(
                    copyToQuestionList.get(i),
                    examToQuestionList.get(i)
            );
            copyQuestionList.add(copyQuestion);
        }
        modelMap.put("questionList",copyQuestionList);
        modelMap.put("status",1);
        modelMap.put("msg","获取成功");
    }
    else {
        modelMap.put("status",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;
}
@RequestMapping(value = "/toWrongBook" ,method = RequestMethod.GET)
    public Map<String,Object> toWrongBook(HttpServletRequest request,Integer copyId,Integer examId){
    Map<String,Object> modelMap = new HashMap<>();
//    Integer copyId = HttpServletRequestUtil.getInt(request,"copyId");
//    Integer examId = HttpServletRequestUtil.getInt(request,"examId");
    String token = request.getHeader("token");
    if(token!=null&&jedisUtilKeys.exists("token")){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);
        modelMap=examService.excuteWrong(user,copyId,examId);
        if (modelMap==null){
            modelMap = new HashMap<>();
             modelMap.put("status",-1);
            modelMap.put("msg","错题本不存在");
            return modelMap;
        }
        modelMap.put("status",1);
        modelMap.put("msg","错题本获取成功");
//        if(copyId!=null){
//            //用户
//        Copy copy =copyMapper.selectByPrimaryKey(copyId);
//
//        }else if(examId!=null){
//
//        }else{
//            modelMap.put("status",-1);
//            modelMap.put("msg","不存在wrongBook");
//        }
    }
    else {
        modelMap.put("status",0);
        modelMap.put("msg","未登录");
    }
    return modelMap;
}
}
