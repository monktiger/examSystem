package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.ExamToQuestion;
import com.monktiger.examsystem.entity.ExamToQuestionKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ExamToQuestionMapper {
    int deleteByPrimaryKey(ExamToQuestionKey key);

    int insert(ExamToQuestion record);

    int insertSelective(ExamToQuestion record);

    ExamToQuestion selectByPrimaryKey(ExamToQuestionKey key);

    int updateByPrimaryKeySelective(ExamToQuestion record);

    int updateByPrimaryKey(ExamToQuestion record);
}