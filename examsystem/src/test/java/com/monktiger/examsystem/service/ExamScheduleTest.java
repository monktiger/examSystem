package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.ExamToQuestion;
import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ExamScheduleTest {

//    @Autowired
//    private ExamMapper examMapper;
//    @Autowired
//    private GroupToExamMapper groupToExamMapper;
//    @Autowired
//    private GroupMapper groupMapper;
//    @Autowired
//    private CopyMapper copyMapper;
//    @Autowired
//    private ExamToQuestionMapper examToQuestionMapper;
//    @Autowired
//    private CopyToQuestionMapper copyToQuestionMapper;
//
//    @Test
//    public void preparedExam(){
//
//        /**
//         *  获取 tb_exam status == 0[未生成copy 的题卷] 的exam list 且时间状况符合逻辑
//         */
//        List<Exam> examList =examMapper.getPrepareExam();
//        if(examList.size()==0){
//            return;
//        }
//
//        /**
//         *  将获取的 该examList 修改status 为1 【即将开始考试】开考前锁定试卷
//         */
//        examMapper.updateExamListStatus(examList,1);
//
//        /**
//         * 遍历该 examList
//         */
//        for(Exam e:examList){
//
//            /**
//             * tb_group_to_exam 将根据 eId 搜索出 gId
//             */
//            List<String> groupIdList = groupToExamMapper.selectByExamId(e.getId());
//
////            /**
////             *  groupIdList
////             */
////            for(String id:groupIdList){
////                System.out.println(id);
////            }
//
//            /**
//             * tb_exam_to_question 根据id 搜索出相关信息
//             */
//            List<ExamToQuestion> examToQuestions = examToQuestionMapper.selectByExamKey(e.getId());
//            Integer totalScore = null;
//            int flag =1;
//
//            /**
//             *  计算总分 exam
//             */
//            for (ExamToQuestion etq: examToQuestions){
//                totalScore+=etq.getScore();
//                if(etq.getType()==5){
//                    flag=0;
//                }
//            }if(flag==1){
//                e.setType(true);
//            }else e.setType(false);
//            e.setScore(totalScore);
//            examMapper.updateByPrimaryKeySelective(e);
//
//            /**
//             * 根据情况 生成 tb_copy tb_copy_to_question
//             */
//            for (String groupId: groupIdList){
//                List<Group> groupList = groupMapper.selectByKeyState(groupId,1);
//
//                /**
//                 * 生成相应答卷
//                 */
//                copyMapper.createCopyList(groupId,groupList,e.getId(),0);
//                System.out.println(groupId);
//                System.out.println(groupList.toString());
//                /**
//                 *  生成对应的题目
//                 */
//                List<Copy> copyList = copyMapper.selectByGroupAndExam(e.getId(),groupId);
//                for(Copy copy:copyList){
//                    for(ExamToQuestion examq:examToQuestions){
//                        copyToQuestionMapper.createQuestion(copy,examq,false);
//                    }
//                }
//            }
//        }
//    }
//
//    @Test
//    public void beginExam(){
//        List<Exam> examList= examMapper.getbeginExam();
//        if(examList.size()==0){
//            return;
//        }
//        examMapper.updateExamListStatus(examList,2);
//        copyMapper.updateByExamList(examList);
//    }
//
//    @Test
//    public void endExam(){
//    List<Exam> examList = examMapper.getEndExam();
//    if(examList.size()==0){
//        return;
//    }
//    examMapper.updateExamListStatus(examList,3);
//    for(Exam e:examList){
//        if (e.getType()==true){
//            copyMapper.updateByExam(e.getId(),2);
//        }else copyMapper.updateByExam(e.getId(),3);
//    }
//    }
}
