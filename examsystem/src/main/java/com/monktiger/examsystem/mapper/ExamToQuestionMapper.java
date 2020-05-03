package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.ExamToQuestion;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbExamToQuestionDAO继承基类
 */

@Mapper
public interface ExamToQuestionMapper  {
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(ExamToQuestion examToQuestion);

    int insertSelective(ExamToQuestion examToQuestion);

    int updateByPrimaryKey(ExamToQuestion examToQuestion);
}