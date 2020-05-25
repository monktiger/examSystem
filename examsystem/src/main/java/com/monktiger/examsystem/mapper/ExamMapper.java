package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exam record);

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
    int updateByPrimaryKey(Exam record);
}