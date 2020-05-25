package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.ExamToQuestion;
import com.monktiger.examsystem.entity.ExamToQuestionKey;
import com.monktiger.examsystem.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExamToQuestionMapper {
    int deleteByPrimaryKey(ExamToQuestionKey key);

    int insert(ExamToQuestion record);

    int insertSelective(ExamToQuestion record);

    ExamToQuestion selectByPrimaryKey(ExamToQuestionKey key);

    List<Question> selectByPrimaryKey(Integer examId);

    ExamToQuestion selectByPrimaryKey(Integer examId, Integer id);

    int updateByPrimaryKeySelective(ExamToQuestion record);

    int updateByPrimaryKey(ExamToQuestion record);
}