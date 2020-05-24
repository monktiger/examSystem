package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbExamDAO继承基类
 */
@Mapper
@Repository
public interface ExamMapper  {
    Exam selectByPrimaryKey(Integer Id);

    int deleteByPrimaryKey(String Id);

    int deleteGroupExamByKey(String id, String groupId);

    int insert(Exam exam);

    int insertSelective(Exam exam);

    int insertGroupExam(String gid,String eid,String begin_time,String end_time);

    int updateByPrimaryKey(Exam exam);

    int updateByPrimaryKeySelective(Exam exam);

    /**
     * selectExamInGroupView
     * @param groupId
     * @return
     */
    List<Exam> selectExamByGroup(String groupId);

    /**
     * 选择现在时间与考试开始时间<15min或者大于开始时间的考试
     * @param groupId
     * @return
     */
    List<Exam> selectExamByGroupAndStatus(String groupId);
}