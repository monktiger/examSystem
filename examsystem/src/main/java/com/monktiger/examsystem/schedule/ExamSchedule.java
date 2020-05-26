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
    List<String> groupIdList = groupToExamMapper.getGroup(e.getId());
       List<ExamToQuestion> examToQuestions = examToQuestionMapper.selectByExam(e.getId());
       for (String groupId: groupIdList){
        List<String> userIdList = groupMapper.selectGroupMember(groupId);
        copyMapper.createCopyList(groupId,userIdList,e.getId(),0);
        List<Integer> copyIdList = copyMapper.selectByExamAndGroup(e.getId(),groupId);
        copyToQuestionMapper.createQuestion(copyIdList,examToQuestions,false);
    }
   }
}

    @Scheduled(cron = "0/10 * * * * ? ")
    public void beginExam(){
    examMapper.beginExam();
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
