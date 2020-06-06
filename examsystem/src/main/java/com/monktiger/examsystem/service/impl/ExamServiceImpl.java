package com.monktiger.examsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.dto.WrongBookQuestion;
import com.monktiger.examsystem.entity.*;
import com.monktiger.examsystem.mapper.*;
import com.monktiger.examsystem.service.ExamService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import com.monktiger.examsystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CopyMapper copyMapper;
    @Autowired
    private GroupToExamMapper groupToExamMapper;
    @Autowired
    private CopyToQuestionMapper copyToQuestionMapper;
    @Autowired
    private ExamToQuestionMapper examToQuestionMapper;
    @Override
    public Exam selectByPrimaryKey(String openId) {
        return null;
    }

    @Override
    public int deleteByPrimaryKey(String openId) {
        return 0;
    }

    @Override
    public int insert(Exam exam) {
        return 0;
    }

    @Override
    public int insertSelective(Exam exam) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Exam exam) {
        return 0;
    }

    @Override
    public int insertAnswer(Exam exam) {
        return 0;
    }

    @Override
    public int solveUserAndExamAssociation(int examId, User user) {
        Exam exam = examMapper.selectByPrimaryKey(examId);
        int min1=TimeUtil.BetweenMin(exam.getEndTime().getTime());
        int min2=TimeUtil.BetweenMin(exam.getBeginTime().getTime());
        //如果是出题人
        //status由mysql event控制
        if(exam.getPublisherId()==user.getOpenId()){
            if(exam.getStatus()==0){
              return 10001;
            }else if(exam.getStatus()==1){
              return 10002;
            }else if(exam.getStatus()==2){
                return 10003;
            }else {
                return 10004;
            }
        }else{
            //如果是学生
            if(exam.getStatus()==1){
                return 20001;
            }else if(exam.getStatus()==2){
                return 20002;
            }else if(exam.getStatus()==3&&exam.getType()==false){
                return 20003;
            }else if(exam.getStatus()==3&&exam.getType()==true){
                Copy copy=copyMapper.selectByAssociaiton(user.getOpenId(),examId);
                if(copy.getStatus()==2){
                    return 20003;
                }else return 20004;
            }
        }
        return 0;
    }

    @Override
    public List<Exam> excuteExamList(String groupId, User user) {
        Group group=groupMapper.selectByPrimaryKey(user.getOpenId(),groupId);
        List<Exam> examList;
        List<Integer> examIdList = groupToExamMapper.selectByGroupId(groupId);

        if(examIdList.size()==0){
            return null;
        }
        if (group.getStatus()==0){//管理员
            examList = examMapper.selectExamByGroup(examIdList);
        }else{//普通成员
            examList = examMapper.selectExamByGroupAndStatus(examIdList);
        }
        return examList;
    }

    @Override
    public Map<String, Object> excuteWrong(User user, Integer copyId, Integer examId) {
        //相当于管理员
        Copy copy;
        Exam exam;
        Map<String,Object> modelMap = new HashMap<>();
        /**
         *  出题人看错题本 看不到
         *  学生 返回错题内容
         */
        if(copyId!=null&&examId==null){
             copy =copyMapper.selectByPrimaryKey(copyId);
            if(copy==null)
                return null;
             exam = examMapper.selectByPrimaryKey(copy.getExamId());
            if(exam==null||exam.getPublisherId()!=user.getOpenId()){
                return null;
            }
        }else if(examId!=null&&copyId==null){//相当于学生
             exam = examMapper.selectByPrimaryKey(examId);
            if (exam==null)
                return null;
           copy = copyMapper.selectByAssociaiton(user.getOpenId(),examId);
            if(copy==null){
                return null;
            }
        }else{
            return null;
        }

        if(exam.getStatus()!=3){
            return null;
        }
        List<CopyToQuestion> copyToQuestionList = copyToQuestionMapper.selectByCopyId(copy.getCopyId());
        List<ExamToQuestion> examToQuestionList = examToQuestionMapper.selectByExamKey(exam.getId());
        List<WrongBookQuestion> wrongBookQuestionList = new ArrayList<>();
        for(int i = 0;i<copyToQuestionList.size();i++){
            WrongBookQuestion wrongBookQuestion = new WrongBookQuestion(
                    copyToQuestionList.get(i),
                    examToQuestionList.get(i)
            );
            wrongBookQuestionList.add(wrongBookQuestion);
        }
        modelMap.put("examName",exam.getName());
        modelMap.put("copyId",copy.getCopyId());
        modelMap.put("judge",copy.getJudge());
        modelMap.put("getScore",copy.getScore());
        modelMap.put("questionList",wrongBookQuestionList);
        return modelMap;
    }

    @Override
    public Map<String, Object> getExam(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>();
        String search = HttpServletRequestUtil.getString(request,"search");
        int pageNum  = HttpServletRequestUtil.getInt(request,"pageNum");
        if(pageNum==0)pageNum=1;
        int total = examMapper.selectExamCount(search);
        Page page = new Page(pageNum,total);
        List<Exam> examList=examMapper.selcetExam(page.getStartIndex(),page.getPageSize(),search);
        modelMap.put("examList",examList);
        modelMap.put("pageNum",pageNum);
        modelMap.put("pages",page.getTotalPage());
        modelMap.put("total",page.getTotalRecord());
        modelMap.put("status",1);
        modelMap.put("msg","返回成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> deleteExam(int examId) {
        Map<String,Object> modelMap = new HashMap<>();
        examMapper.deleteByPrimaryKey(examId);
        examToQuestionMapper.deletByExamId(examId);
        List<Copy> copy = copyMapper.selectByGroupAndExam(examId,null);
        if(copy.size()!=0){
        copyMapper.deleteByexam(examId);
        copyToQuestionMapper.deleteByCopyList(copy);}
        modelMap.put("status",1);
        modelMap.put("msg","返回成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> updateExam(int examId, String jsonString) {
        Map<String,Object> modelMap = new HashMap<>();
        JSONObject json = JSON.parseObject(jsonString);
        Exam exam = json.toJavaObject(Exam.class);
        exam.setId(examId);
        examMapper.updateByPrimaryKeySelective(exam);
        modelMap.put("status",1);
        modelMap.put("msg","返回成功");
        return modelMap;
    }
}
