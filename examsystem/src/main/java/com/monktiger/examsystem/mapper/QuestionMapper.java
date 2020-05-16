package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbQuestionDAO继承基类
 */
@Mapper
@Repository
public interface QuestionMapper {

  Question selectByPrimaryKey(Integer id);

   int  deleteByPrimaryKey(Integer id);

   int insert(Integer id);

   int insertSelective(Question question);

   int updateByPrimaryKeySelective(Question question);

   int updateByPrimaryKey(Question question);

 /**
  * 获得题库题目总数
  * @param search
  * @param category
  * @param type
  * @return
  */
   int getTotalQuestion(@Param("search") String search, @Param("category") String category, @Param("type") Integer type);

    /**
     * 获得题库中对应题目
     * @param startIndex
     * @param pageSize
     * @param search
     * @param category
     * @param type
     * @return
     */
   List<Question> getQuestion(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("search") String search, @Param("category") String category, @Param("type") Integer type);

}