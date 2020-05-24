package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.CopyMapper;
import com.monktiger.examsystem.mapper.ExamMapper;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.service.ExamService;
import com.monktiger.examsystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CopyMapper copyMapper;
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
                Copy copy=copyMapper.selectByPrimaryKey(user.getOpenId(),examId);
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
        if (group.getStatus()==1){//管理员
            examList = examMapper.selectExamByGroup(groupId);
        }else{//普通成员
            examList = examMapper.selectExamByGroupAndStatus(groupId);
        }
        return examList;
    }
}
