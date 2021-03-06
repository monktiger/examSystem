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

    /**
     *  准备考试Exam
     */

@Scheduled(cron = "0/10 * * * * ? ")
    public void prepareExam(){

    /**
     *  获取 tb_exam status == 0 的exam list 且时间状况符合逻辑
     */
    List<Exam> examList =examMapper.getPrepareExam();
   if(examList.size()==0){
       return;
   }

    /**
     *  将获取的 该examLisxt 修改status 为1
     */
   examMapper.updateExamListStatus(examList,1);
///////////////////////////////////////////
    /**
     * 遍历该 examList
     */
   for(Exam e:examList){

       /**
        * tb_group_to_exam 将根据 eId 搜索出 gId
        */
       List<String> groupIdList = groupToExamMapper.selectByExamId(e.getId());
       /**
        * tb_exam_to_question 根据id 搜索出相关信息
        */
       List<ExamToQuestion> examToQuestions = examToQuestionMapper.selectByExamKey(e.getId());
       Integer totalScore = null;
       int flag =1;

       /**
        *  计算总分 exam
        */
       if(examToQuestions!=null)
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

       /**
        * 根据情况 生成 tb_copy tb_copy_to_question
        */
       for (String groupId: groupIdList){
        List<Group> groupList = groupMapper.selectByKeyState(groupId,1);

        /**
         * 生成相应答卷
         */
         //copyMapper.createCopyList(groupId,groupList,e.getId(),0);
          copyMapper.createCopyList2(groupList,e.getId(),0);

        /**
         *  生成对应的题目
         */
        List<Copy> copyList = copyMapper.selectByGroupAndExam(e.getId(),groupId);
        for(Copy copy:copyList){
            for(ExamToQuestion examq:examToQuestions){
                copyToQuestionMapper.createQuestion(copy,examq,false);
            }
        }
    }
   }
}

    /**
     * 开始考试
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    public void beginExam(){
    List<Exam> examList= examMapper.getbeginExam();
        if(examList.size()==0){
            return;
        }
    examMapper.updateExamListStatus(examList,2);
    copyMapper.updateByExamList(examList);
    }


    /**
     * 考试结束
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    public void endExam(){
    System.out.println("hello i'm in endExam");
    List<Exam> examList = examMapper.getEndExam();
    if(examList.size()==0){
        return;
    }
    examMapper.updateExamListStatus(examList,3);
    for(Exam e:examList){
        if (e.getType()==true){
            copyMapper.updateByExam(e.getId(),2);
        }else copyMapper.updateByExam(e.getId(),3);
    }
    }
}
