package com.monktiger.examsystem.schedule;


import com.monktiger.examsystem.entity.*;
import com.monktiger.examsystem.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExamSchedule {
@Autowired
private ExamMapper examMapper;
@Autowired
private GroupToExamMapper groupToExamMapper;
@Autowired
private GroupMapper groupMapper;
@Autowired
private CopyMapper copyMapper;
@Autowired
private ExamToQuestionMapper examToQuestionMapper;
@Autowired
private CopyToQuestionMapper copyToQuestionMapper;
@Scheduled(cron = "0/10 * * * * ? ")
    public void prepareExam(){
   List<Exam> examList =examMapper.getPrepareExam();
   examMapper.updateexamListStatus(examList,1);
   for(Exam e:examList){
    List<String> groupIdList = groupToExamMapper.selectByExamId(e.getId());
       List<ExamToQuestion> examToQuestions = examToQuestionMapper.selectByExamKey(e.getId());
       Integer totalScore = null;
       int flag =1;
       for (ExamToQuestion etq: examToQuestions){
           totalScore+=etq.getScore();
           if(etq.getType()==5){
               flag=0;
           }
       }if(flag==1){
           e.setType(true);
       }else e.setType(false);
       e.setScore(totalScore);
       examMapper.updateByPrimaryKeySelective(e);
       for (String groupId: groupIdList){
        List<Group> groupList = groupMapper.selectByKeyState(groupId,1);
        copyMapper.createCopyList(groupId,groupList,e.getId(),0);
        List<Copy> copyList = copyMapper.selectByGroupAndExam(e.getId(),groupId);
        copyToQuestionMapper.createQuestion(copyList,examToQuestions,false);
    }
   }
}

    @Scheduled(cron = "0/10 * * * * ? ")
    public void beginExam(){
   List<Exam> examList= examMapper.getbeginExam();
   examMapper.updateexamListStatus(examList,2);
    copyMapper.updateByExamList(examList);
    }

    @Scheduled(cron = "0/10 * * * * ? ")
    public void endExam(){
    List<Exam> examList = examMapper.getEndExam();
    examMapper.updateexamListStatus(examList,3);
    for(Exam e:examList){
        if (e.getType()==true){
            copyMapper.updateByExam(e.getId(),2);
        }else copyMapper.updateByExam(e.getId(),3);
    }
    }
}
