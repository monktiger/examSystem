package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbQuestionDAO继承基类
 */
@Mapper
public interface QuestionMapper {

  Question selectByPrimaryKey(Integer id);

   int  deleteByPrimaryKey(Integer id);

   int insert(Integer id);

   int insertSelective(Question question);

   int updateByPrimaryKeySelective(Question question);

   int updateByPrimaryKey(Question question);

}