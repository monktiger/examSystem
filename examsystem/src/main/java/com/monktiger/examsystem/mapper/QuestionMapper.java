package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    int getTotalQuestion(@Param("search") String search, @Param("category") String category, @Param("type") Integer type);

    List<Question> getQuestion(@Param("index") int index, @Param("size") int size, @Param("search") String search, @Param("category") String category, @Param("type") Integer type);

    int getQuestionCount(String search);

    List<Question> getTotal(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("search") String search);
}