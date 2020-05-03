package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbExamDAO继承基类
 */
@Mapper
public interface ExamMapper  {
    User selectByPrimaryKey(String Id);

    int deleteByPrimaryKey(String Id);

    int insert(Exam exam);

    int insertSelective(Exam exam);

    int updateByPrimaryKey(Exam exam);
}