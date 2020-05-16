package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbExamDAO继承基类
 */
@Mapper
@Repository
public interface ExamMapper  {
    User selectByPrimaryKey(String Id);

    int deleteByPrimaryKey(String Id);

    int deleteGroupExamByKey(String id, String groupId);

    int insert(Exam exam);

    int insertSelective(Exam exam);

    int insertGroupExam(String gid,String eid,String begin_time,String end_time);

    int updateByPrimaryKey(Exam exam);

    int updateByPrimaryKeySelective(Exam exam);
}