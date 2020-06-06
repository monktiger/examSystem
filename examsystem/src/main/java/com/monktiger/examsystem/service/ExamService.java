package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ExamService {

/***************************************************出题者角度***************************************************************/

    Exam selectByPrimaryKey(String id);

    int deleteByPrimaryKey(String openId);

    int insert(Exam exam);

    int insertSelective(Exam exam);

    int updateByPrimaryKey(Exam exam);


/******************************************************答题者角度************************************************************/

   int insertAnswer(Exam exam);

    /**
     * 处理用户与某张试卷的关系
     * @param examId
     * @param user
     * @return
     */
    int solveUserAndExamAssociation(int examId,User user);

    /**
     * 生成特定用户在特定组的试卷列表
     * @param groupId
     * @param user
     * @return
     */
    List<Exam> excuteExamList(String groupId,User user);

    /**
     *
     * @return
     * @param user
     * @param copyId
     * @param examId
     */
    Map<String,Object> excuteWrong( User user, Integer copyId, Integer examId);

    /////adminMethod
    Map<String, Object> getExam(HttpServletRequest request);

    Map<String, Object> deleteExam(int examId);

    Map<String, Object> updateExam(int examId, String jsonString);
}
