package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Question;
import org.apache.ibatis.annotations.Mapper;
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

    int getTotalQuestion(String search, String category, Integer type);

    List<Question> getQuestion(int index, int size, String search, String category, Integer type);
}